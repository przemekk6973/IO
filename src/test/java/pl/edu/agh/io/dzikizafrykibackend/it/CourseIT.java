package pl.edu.agh.io.dzikizafrykibackend.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.io.dzikizafrykibackend.model.CourseCreationResource;
import pl.edu.agh.io.dzikizafrykibackend.model.CourseResource;
import pl.edu.agh.io.dzikizafrykibackend.model.DateResource;
import pl.edu.agh.io.dzikizafrykibackend.util.WeekEnum;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class CourseIT extends BaseIT {

    private final CourseCreationResource courseCreationResource1 = CourseCreationResource.builder()
            .name("Example course")
            .description("Example course description")
            .dates(Set.of(
                    new DateResource(WeekEnum.MONDAY, LocalTime.of(12, 0), LocalTime.of(12, 30)),
                    new DateResource(WeekEnum.THURSDAY, LocalTime.of(13, 0), LocalTime.of(14, 30))
            ))
            .build();

    private final CourseCreationResource courseCreationResource2 = CourseCreationResource.builder()
            .name("Example course 2")
            .description("Example course description 2")
            .dates(Set.of(
                    new DateResource(WeekEnum.WEDNESDAY, LocalTime.of(12, 0), LocalTime.of(12, 30)),
                    new DateResource(WeekEnum.FRIDAY, LocalTime.of(13, 0), LocalTime.of(14, 30))
            ))
            .build();

    @BeforeEach
    public void setup() throws IOException {
        dsl().purgeEntities();
        dsl().setupUser(TEACHER1_ID);
        dsl().setupUser(STUDENT1_ID);
    }

    @Test
    void shouldCreateCourse() throws IOException {
        // when
        CourseResource course = dsl().useClientAsUser(TEACHER1_ID).postCourse(courseCreationResource1).execute().body();

        // then
        assertThat(course).isNotNull();
        assertThat(course.getTeacher()).isEqualTo(TEACHER1_ID.email());
        assertThat(course.getDates()).isEqualTo(courseCreationResource1.getDates());

        assertThat(dsl().getCourseRepository().findAll()).hasSize(1);
    }

    @Test
    void shouldGetCourses() throws IOException {
        // when
        dsl().useClientAsUser(TEACHER1_ID).postCourse(courseCreationResource1).execute().body();
        dsl().useClientAsUser(TEACHER1_ID).postCourse(courseCreationResource2).execute().body();

        // then
        List<CourseResource> courses = dsl().useClientAsUser(TEACHER1_ID).getOwnedCourses().execute().body();
        assertThat(courses).hasSize(2);
    }

    @Test
    void shouldGetSingleCourseAsTeacher() throws IOException {
        // when
        CourseResource createdCourse = dsl().useClientAsUser(TEACHER1_ID)
                .postCourse(courseCreationResource1)
                .execute()
                .body();

        // then
        assert createdCourse != null;
        CourseResource courseResponse = dsl().useClientAsUser(TEACHER1_ID)
                .getCourse(createdCourse.getCourseId())
                .execute()
                .body();
        assertThat(courseResponse).isNotNull();
        assertThat(courseResponse.getCourseId()).isEqualTo(createdCourse.getCourseId());
    }

    @Test
    void shouldGetSingleCourseAsStudent() throws IOException {
        // given
        CourseResource createdCourse = dsl().useClientAsUser(TEACHER1_ID)
                .postCourse(courseCreationResource1)
                .execute()
                .body();
        assert createdCourse != null;
        dsl().useClientAsUser(STUDENT1_ID).postEnroll(createdCourse.getCourseId()).execute();

        // when
        CourseResource course = dsl().useClientAsUser(STUDENT1_ID)
                .getCourse(createdCourse.getCourseId())
                .execute()
                .body();

        // then
        assertThat(course.getCourseId()).isEqualTo(createdCourse.getCourseId());
    }
}
