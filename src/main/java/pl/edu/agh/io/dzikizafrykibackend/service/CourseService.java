package pl.edu.agh.io.dzikizafrykibackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.CourseRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.UserRepository;
import pl.edu.agh.io.dzikizafrykibackend.exception.CourseMissingException;
import pl.edu.agh.io.dzikizafrykibackend.exception.InvalidOwnerException;
import pl.edu.agh.io.dzikizafrykibackend.exception.ValidationException;
import pl.edu.agh.io.dzikizafrykibackend.model.Course;
import pl.edu.agh.io.dzikizafrykibackend.model.CourseCreationResource;
import pl.edu.agh.io.dzikizafrykibackend.model.DateResource;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;


    @Transactional
    public Course getCourse(UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(CourseMissingException::new);
        return Course.fromEntity(course);
    }

    @Transactional
    public List<Course> getOwnedCoursesAsTeacher(User userContext) {
        return courseRepository.findAllByTeacherId(userContext.getId()).stream().map(Course::fromEntity).toList();
    }

    public List<Course> getAssignedCoursesAsStudent(User userContext) {
        return userRepository.findById(userContext.getId())
                .orElseThrow()
                .getAssignedCourses()
                .stream()
                .map(Course::fromEntity)
                .toList();
    }

    @Transactional
    public void deleteCourse(User userContext, UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(CourseMissingException::new);
        if (course.getTeacher().equals(userContext)) {
            courseRepository.delete(course);
        } else {
            throw new InvalidOwnerException();
        }
    }

    @Transactional
    public Course postCourse(User userContext, CourseCreationResource courseCreationResource) {
        CourseEntity courseEntity = CourseEntity.builder()
                .courseName(courseCreationResource.getName())
                .description(courseCreationResource.getDescription())
                .teacher(userContext)
                .build();

        Set<DateEntity> dates = courseCreationResource.getDates()
                .stream()
                .map(dateResource -> DateResource.toEntity(dateResource, courseEntity))
                .collect(Collectors.toSet());

        courseEntity.setDates(dates);

        return Course.fromEntity(courseRepository.save(courseEntity));
    }

    @Transactional
    public Course getCourseAsTeacher(User userContext, UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValidationException("Course does not exist."));

        if (course.getTeacher().equals(userContext)) {
            return Course.fromEntity(course);
        } else {
            throw new ValidationException("You do not have access to this course.");
        }
    }

    @Transactional
    public Course getCourseAsStudent(User userContext, UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValidationException("Course does not exist."));

        if (course.getStudents().contains(userContext)) {
            return Course.fromEntity(course);
        } else {
            throw new ValidationException("You do not have access to this course.");
        }
    }

}
