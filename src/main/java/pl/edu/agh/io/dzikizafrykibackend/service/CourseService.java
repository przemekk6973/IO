package pl.edu.agh.io.dzikizafrykibackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.CourseRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.DateRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.UserRepository;
import pl.edu.agh.io.dzikizafrykibackend.exception.CourseMissingException;
import pl.edu.agh.io.dzikizafrykibackend.exception.CourseNameMissingException;
import pl.edu.agh.io.dzikizafrykibackend.exception.InvalidOwnerException;
import pl.edu.agh.io.dzikizafrykibackend.model.Course;
import pl.edu.agh.io.dzikizafrykibackend.model.CourseUpdate;
import pl.edu.agh.io.dzikizafrykibackend.model.DateResource;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final DateRepository dateRepository;

    @Transactional
    public Course getCourse(UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(CourseMissingException::new);
        return Course.fromEntity(course);
    }

    @Transactional
    public List<Course> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(Course::fromEntity)
                .toList();
    }

    @Transactional
    public void deleteCourse(UserDetails userDetails, UUID courseId) {
        courseRepository.findById(courseId)
                .map(course -> validateOwner(course, userDetails))
                .orElseThrow(CourseMissingException::new);

        courseRepository.deleteById(courseId);
    }

    @Transactional
    public Course postCourse(UserDetails userDetails, CourseUpdate course) {
        if (course.getName().isEmpty() || course.getDescription().isEmpty()) {
            throw new CourseNameMissingException();
        }
        User teacher = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        CourseEntity courseEntity = CourseEntity.builder()
                .name(course.getName().get())
                .description(course.getDescription().get())
                .teacher(teacher)
                .build();

        return Course.fromEntity(courseRepository.save(courseEntity));
    }

    @Transactional
    public Course putCourse(UserDetails userDetails, UUID courseId, CourseUpdate update) {
        return courseRepository.findById(courseId)
                .map(course -> validateOwner(course, userDetails))
                .map(course -> updateCourse(course, update))
                .map(Course::fromEntity)
                .orElseThrow(CourseMissingException::new);
    }

    private CourseEntity validateOwner(CourseEntity courseEntity, UserDetails userDetails) {
        User teacher = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        if (!Objects.equals(courseEntity.getTeacher(), teacher)) {
            throw new InvalidOwnerException();
        }
        return courseEntity;
    }

    private CourseEntity updateCourse(CourseEntity entity, CourseUpdate update) {
        update.getName().ifPresent(entity::setName);
        update.getDescription().ifPresent(entity::setDescription);
        update.getUsers().map(this::usersFrom).ifPresent(entity::setStudents);
        update.getDates().map(this::datesFrom).ifPresent(entity::setDates);
        courseRepository.save(entity);
        return entity;
    }

    private Set<User> usersFrom(Set<String> emails) {
        return emails.stream()
                .map(userRepository::findByEmail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Set<DateEntity> datesFrom(Set<DateResource> resources) {
        return resources.stream()
                .map(this::dateFrom)
                .collect(Collectors.toSet());
    }

    private DateEntity dateFrom(DateResource resource) {
        DateEntity entity = DateEntity.builder()
                .weekDay(resource.getWeekDay())
                .startTime(resource.getStartTime())
                .endTime(resource.getEndTime())
                .build();
        return dateRepository.save(entity);
    }
}
