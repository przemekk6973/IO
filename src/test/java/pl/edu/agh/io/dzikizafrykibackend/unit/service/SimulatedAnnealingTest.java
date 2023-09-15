package pl.edu.agh.io.dzikizafrykibackend.unit.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.SimulatedAnnealing;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.Solution;


public class SimulatedAnnealingTest {

    @Test
    public void testSolveWithGeneratedPreferences() {
        // Generate random preferences for testing
        ArrayList<ArrayList<Boolean>> preferences = generateRandomPreferences(50, 10, 0.6);

        // Initialize the SimulatedAnnealing instance
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(preferences);

        // Solve the problem
        Solution solution = simulatedAnnealing.solve(3);

        assertNotNull(solution);
        assertEquals(3, solution.getGroups().size());
        System.out.println(solution);

    }

    private ArrayList<ArrayList<Boolean>> generateRandomPreferences(int numberOfStudents, int numberOfGroups, double preferenceProbability) {
        ArrayList<ArrayList<Boolean>> preferences = new ArrayList<>();
        for (int i = 0; i < numberOfStudents; i++) {
            preferences.add(new ArrayList<>());
            for (int j = 0; j < numberOfGroups; j++) {
                preferences.get(i).add(Math.random() < preferenceProbability);
            }
        }
        return preferences;
    }
}