package pl.edu.agh.io.dzikizafrykibackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.CourseRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.UserRepository;
import pl.edu.agh.io.dzikizafrykibackend.exception.ValidationException;
import pl.edu.agh.io.dzikizafrykibackend.model.Course;
import pl.edu.agh.io.dzikizafrykibackend.model.StudentPreferencesResource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseEnrollmentService {


    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseEnrollmentService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course enrollStudent(User userContext, UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValidationException("Course does not exist."));

        course.getStudents().add(userContext);
        return Course.fromEntity(courseRepository.save(course));
    }

    @Transactional
    public StudentPreferencesResource saveStudentPreferences(
            User userContext,
            StudentPreferencesResource studentPreferencesResource
    ) {
        User student = userRepository.findById(userContext.getId()).orElseThrow();
        CourseEntity course = courseRepository.findById(studentPreferencesResource.getCourseId())
                .orElseThrow(() -> new ValidationException("Course does not exist."));

        if (!course.getStudents().contains(student)) {
            throw new ValidationException("You do not have access to this course.");
        }

        Set<DateEntity> dates = new HashSet<>();
        studentPreferencesResource.getDatesIds().forEach(uuid -> {
            dates.addAll(course.getDates().stream().filter(dateEntity -> dateEntity.getId().equals(uuid)).toList());
        });

        student.setUserDates(dates);
        userRepository.save(student);

        return StudentPreferencesResource.toDto(course.getId(), dates);
    }

    public List<StudentPreferencesResource> getAllPreferences(User userContext) {
        Set<DateEntity> dates = userRepository.findById(userContext.getId()).orElseThrow().getUserDates();
        return dates.stream()
                .collect(Collectors.groupingBy(DateEntity::getCourse))
                .entrySet().stream()
                .map(entry -> StudentPreferencesResource.toDto(entry.getKey().getId(), entry.getValue()))
                .toList();
    }
}
