package pl.edu.agh.io.dzikizafrykibackend.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.SimulatedAnnealing;
import pl.edu.agh.io.dzikizafrykibackend.service.algorithm.Solution;

public class SolutionTest {

    private ArrayList<ArrayList<Boolean>> preferences;
    private Solution solution;

    @BeforeEach
    public void setUp() {
        // Przygotowanie danych do testów
        preferences = new ArrayList<>();
        preferences.add(new ArrayList<>(Arrays.asList(true, false, true))); // Student 0
        preferences.add(new ArrayList<>(Arrays.asList(false, true, true))); // Student 1
        preferences.add(new ArrayList<>(Arrays.asList(true, true, false))); // Student 2

        // Inicjalizacja rozwiązania
        solution = new Solution(preferences, 3);
    }

    @Test
    public void testGetGroups() {
        ArrayList<ArrayList<Integer>> groups = solution.getGroups();
        assertNotNull(groups);
        assertEquals(3, groups.size());
    }

    @Test
    public void testGetGroupIds() {
        ArrayList<Integer> groupIds = solution.getGroupsIds();
        assertNotNull(groupIds);
        assertEquals(3, groupIds.size());
    }


    @Test
    public void testGenerateNeighbour() {
        // Testujemy czy metoda generateNeighbour nie rzuca wyjątku
        assertDoesNotThrow(() -> solution.generateNeighbour(0.5));
    }


    @Test
    public void testSortGroups() {
        // Testujemy czy metoda sortGroups nie rzuca wyjątku
        assertDoesNotThrow(() -> solution.sortGroups());
    }

    @Test
    public void testToString() {
        // Testujemy czy metoda toString nie zwraca null
        assertNotNull(solution.toString());
    }
}
