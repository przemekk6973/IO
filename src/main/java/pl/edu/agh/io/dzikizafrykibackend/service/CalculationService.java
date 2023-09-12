package pl.edu.agh.io.dzikizafrykibackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.GroupGenerator;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.Solution;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.SimulatedAnnealing;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.CourseRepository;
import pl.edu.agh.io.dzikizafrykibackend.exception.ValidationException;
import pl.edu.agh.io.dzikizafrykibackend.db.jsonb.CalculationResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

        List<User> students = new ArrayList<>(course.getStudents());
        List<DateEntity> dates = new ArrayList<>(course.getDates());

        ArrayList<ArrayList<Boolean>> preferences = prepareStudentPreferences(students, dates);
        int numberOfGroups = course.getGroupCount();
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(preferences);
        Solution solution = simulatedAnnealing.solve(numberOfGroups);

        CalculationResults result = createCalculationResults(solution, dates);

        course.setCalculated(true);
        course.setCalculationResults(result);
        return courseRepository.save(course).getCalculationResults();
    }

    private ArrayList<ArrayList<Boolean>> prepareStudentPreferences(List<User> students, List<DateEntity> dates) {
        ArrayList<ArrayList<Boolean>> preferences = new ArrayList<>();

        for (User student : students) {
            ArrayList<Boolean> studentPreferences = new ArrayList<>();

            for (DateEntity date : dates) {
                // Sprawdź, czy student jest zapisany na ten kurs i tę datę
                boolean isStudentEnrolled = student.getUserDates().contains(date);

                studentPreferences.add(isStudentEnrolled);
            }

            preferences.add(studentPreferences);
        }

        return preferences;
    }


    private CalculationResults createCalculationResults(Solution solution, List<DateEntity> dates) {
        CalculationResults result = new CalculationResults();
        Map<UUID, List<UUID>> dateToStudents = result.getDateToStudents();

        for (int i = 0; i < dates.size(); i++) {
            DateEntity date = dates.get(i);
            List<UUID> studentsInGroup = new ArrayList<>();
            List<Integer> group = solution.getGroups().get(i);
            for (int studentIndex : group) {
                studentsInGroup.add(students.get(studentIndex).getId());
            }
            dateToStudents.put(date.getId(), studentsInGroup);
        }

        return result;
    }

    private static double[][] convertBooleanArrayToDoubleArray(List<List<Boolean>> preferences) {
        int numRows = preferences.size();
        int numCols = preferences.get(0).size();
        double[][] doubleArray = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                doubleArray[i][j] = preferences.get(i).get(j) ? 1.0 : 0.0;
            }
        }

        return doubleArray;
    }


    @Transactional
    public CalculationResults getResults(User userContext, UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValidationException("Course does not exist."));
        if (!course.isCalculated()) {
            throw new ValidationException("Course is not yet calculated.");
        }
        if (!course.getTeacher().equals(userContext)) {
            throw new ValidationException("You do not have access to this course.");
        }

        return course.getCalculationResults();
    }
}
