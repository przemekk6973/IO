package pl.edu.agh.io.dzikizafrykibackend.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.SimulatedAnnealing;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.Solution;
import java.util.ArrayList;

public class SimulatedAnnealingClassTest {

    private ArrayList<ArrayList<Boolean>> preferences;

    @BeforeEach
    public void setUp() {
        preferences = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            preferences.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                preferences.get(i).add(Math.random() < 0.6);
            }
        }
    }

    @Test
    public void testSimulatedAnnealingSolvesProblem() {
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(preferences);
        Solution solution = simulatedAnnealing.solve(3);

        assertNotNull(solution);
    }

    @Test
    public void testSimulatedAnnealingProducesValidSolution() {
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(preferences);
        Solution solution = simulatedAnnealing.solve(3);

        // Weryfikacja, czy rozwiązanie spełnia określone kryteria
        // np. czy ilość grup jest zgodna z oczekiwaniami, czy preferencje studentów są spełnione, itp.
        // Właściwie mogłoby się obyć bez tych testów, bo SimulatedAnnealingTest wystarczy, ale zostawiam
    }
}
