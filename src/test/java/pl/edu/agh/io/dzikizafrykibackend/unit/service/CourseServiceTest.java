package pl.edu.agh.io.dzikizafrykibackend.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserRole;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.CourseRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.DateRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.UserRepository;
import pl.edu.agh.io.dzikizafrykibackend.exception.CourseMissingException;
import pl.edu.agh.io.dzikizafrykibackend.model.CourseCreationResource;
import pl.edu.agh.io.dzikizafrykibackend.service.CourseService;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    private static final String COURSE_NAME = "test";
    private static final UUID COURSE_ID = UUID.randomUUID();
    private static final User TEACHER = new User("aaa@bb.cc", "aaa", "bbb", UserRole.TEACHER, null, true, "aaa");
    private static final CourseEntity COURSE_ENTITY = CourseEntity.builder()
            .id(COURSE_ID)
            .courseName(COURSE_NAME)
            .teacher(TEACHER)
            .build();
    private static final Set<User> EMPTY_STUDENTS_SET = Set.of();
    private static final Set<DateEntity> EMPTY_DATE_SET = Set.of();

    @Mock
    private CourseRepository courseRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private DateRepository dateRepositoryMock;

    @InjectMocks
    private CourseService courseService;

    private static Stream<Arguments> providerForPostTest() {
        return Stream.of(
                Arguments.of(CourseCreationResource.builder()
                                     .name(COURSE_NAME)
                                     .description("description")
                                     .dates(Set.of())
                                     .build(),
                             CourseEntity.builder()
                                     .courseName(COURSE_NAME)
                                     .description("description")
                                     .teacher(TEACHER)
                                     .dates(Set.of())
                                     .students(Set.of())
                                     .build())
        );
    }

    @Test
    void shouldGetCourse() {
        // given
        when(courseRepositoryMock.findById(COURSE_ID)).thenReturn(Optional.of(
                new CourseEntity(COURSE_ID, COURSE_NAME, "", TEACHER, EMPTY_STUDENTS_SET, EMPTY_DATE_SET)
        ));

        // when
        courseService.getCourse(COURSE_ID);

        // then
        verify(courseRepositoryMock).findById(COURSE_ID);
    }

    @Test
    void shouldThrowWhenGetCourseOnNonExistentCourse() {
        // given
        when(courseRepositoryMock.findById(COURSE_ID)).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThrows(CourseMissingException.class, () -> courseService.getCourse(COURSE_ID));
        verify(courseRepositoryMock).findById(COURSE_ID);
    }

    @Test
    void shouldGetAllCourses() {
        // given

        // when
        courseService.getOwnedCourses(TEACHER);

        // then
        verify(courseRepositoryMock).findAllByTeacherId(TEACHER.getId());
    }

    @Test
    void shouldDeleteCourse() {
        // given
        when(courseRepositoryMock.findById(COURSE_ID)).thenReturn(Optional.ofNullable(COURSE_ENTITY));

        // when
        courseService.deleteCourse(TEACHER, COURSE_ID);

        // then
        assert COURSE_ENTITY != null;
        verify(courseRepositoryMock).delete(COURSE_ENTITY);
    }

    @Test
    void shouldThrowWhenDeleteNonExistentCourse() {
        // given
        // when
        // then
        Assertions.assertThrows(CourseMissingException.class, () -> courseService.deleteCourse(TEACHER, COURSE_ID));
        verify(courseRepositoryMock, never()).deleteById(COURSE_ID);
    }

    @ParameterizedTest
    @MethodSource("providerForPostTest")
    void shouldPostCourse(CourseCreationResource courseCreationResource, CourseEntity expected) {
        // given
        for (DateEntity date : expected.getDates()) {
            when(dateRepositoryMock.save(date)).thenReturn(date);
        }
        when(courseRepositoryMock.save(expected)).thenReturn(expected);

        // when
        courseService.postCourse(TEACHER, courseCreationResource);

        // then
        InOrder io = getIo();
        for (DateEntity date : expected.getDates()) {
            io.verify(dateRepositoryMock).save(date);
        }
        io.verify(courseRepositoryMock).save(expected);
        io.verifyNoMoreInteractions();
    }

    private InOrder getIo() {
        return inOrder(dateRepositoryMock, courseRepositoryMock, userRepositoryMock);
    }

}
