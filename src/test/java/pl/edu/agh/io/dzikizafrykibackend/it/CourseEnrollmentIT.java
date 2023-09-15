package pl.edu.agh.io.dzikizafrykibackend.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.model.CourseCreationResource;
import pl.edu.agh.io.dzikizafrykibackend.model.CourseResource;
import pl.edu.agh.io.dzikizafrykibackend.model.DateResource;
import pl.edu.agh.io.dzikizafrykibackend.util.WeekEnum;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseEnrollmentIT extends BaseIT {

    private final CourseCreationResource courseCreationResource = CourseCreationResource.builder()
            .name("Example course")
            .description("Example course description")
            .dates(Set.of(
                    new DateResource(WeekEnum.MONDAY, LocalTime.of(12, 0), LocalTime.of(12, 30)),
                    new DateResource(WeekEnum.THURSDAY, LocalTime.of(13, 0), LocalTime.of(14, 30))
            ))
            .build();

    @BeforeEach
    public void setup() throws IOException {
        dsl().purgeEntities();
        dsl().setupUser(TEACHER1_ID);
        dsl().setupUser(STUDENT1_ID);
    }

    @Test
    @Transactional
    void shouldEnroll() throws IOException {
        // given
        CourseResource createdCourse = dsl().useClientAsUser(TEACHER1_ID)
                .postCourse(courseCreationResource)
                .execute()
                .body();

        // when
        assert createdCourse != null;
        dsl().useClientAsUser(STUDENT1_ID).postEnroll(createdCourse.getCourseId()).execute();

        // then
        User student = dsl().getUserRepository().findByEmail(STUDENT1_ID.email()).orElseThrow();
        Set<CourseEntity> enrolledCourses = student.getAssignedCourses();
        assertThat(enrolledCourses).hasSize(1);
        assertThat(enrolledCourses.stream().toList().get(0).getId()).isEqualTo(createdCourse.getCourseId());
    }
}
