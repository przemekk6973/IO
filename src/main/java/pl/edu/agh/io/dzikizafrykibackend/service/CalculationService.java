package pl.edu.agh.io.dzikizafrykibackend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.jsonb.CalculationResults;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.CourseRepository;
import pl.edu.agh.io.dzikizafrykibackend.exception.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final CourseRepository courseRepository;

    @Transactional
    public CalculationResults performMockCalculation(User userContext, UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValidationException("Course does not exist."));

        if (!course.getTeacher().equals(userContext)) {
            throw new ValidationException("You do not have access to this course.");
        }
        if (course.isCalculated()) {
            throw new ValidationException("Course is already calculated.");
        }

        List<User> students = course.getStudents().stream().toList();
        List<DateEntity> dates = course.getDates().stream().toList();

        int datesSize = dates.size();
        List<List<User>> userDividedByDates = new ArrayList<>();
        IntStream.range(0, datesSize).forEach(value -> userDividedByDates.add(new ArrayList<>()));
        for (int i = 0; i < students.size(); i++) {
            userDividedByDates.get(i % datesSize).add(students.get(i));
        }

        CalculationResults result = new CalculationResults(new HashMap<>());
        dates.forEach(
                dateEntity -> result.getDateToStudents().put(
                        dateEntity.getId(),
                        userDividedByDates.remove(0).stream().map(User::getId).toList()
                )
        );

        course.setCalculated(true);
        course.setCalculationResults(result);
        return courseRepository.save(course).getCalculationResults();
    }

    @Transactional
    public CalculationResults getResults(User userContext, UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValidationException("Course does not exist."));
        if (course.isCalculated()) {
            throw new ValidationException("Course is already calculated.");
        }
        switch (userContext.getRole()) {
            case STUDENT -> {
                if (!course.getTeacher().equals(userContext)) {
                    throw new ValidationException("You do not have access to this course.");
                }
            }
            case TEACHER -> {
                if (!course.getStudents().contains(userContext)) {
                    throw new ValidationException("You do not have access to this course.");
                }
            }
        }

        return course.getCalculationResults();
    }
}
