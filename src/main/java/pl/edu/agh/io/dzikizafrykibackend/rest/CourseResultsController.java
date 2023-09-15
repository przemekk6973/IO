package pl.edu.agh.io.dzikizafrykibackend.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserRole;
import pl.edu.agh.io.dzikizafrykibackend.db.jsonb.CalculationResults;
import pl.edu.agh.io.dzikizafrykibackend.service.CalculationService;

import java.util.UUID;


@RestController
@RequestMapping("/course-results")
public class CourseResultsController {

    private final CalculationService calculationService;

    @Autowired
    public CourseResultsController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping("/calculate/{courseId}")
    @Secured({UserRole.ROLE_TEACHER})
    public CalculationResults calculateGroups(Authentication authentication, @PathVariable UUID courseId) {
        User userContext = (User) authentication.getPrincipal();
        return calculationService.performMockCalculation(userContext, courseId);
    }

    @GetMapping("/{courseId}")
    @Secured({UserRole.ROLE_STUDENT, UserRole.ROLE_TEACHER})
    public CalculationResults getResults(Authentication authentication, @PathVariable UUID courseId) {
        User userContext = (User) authentication.getPrincipal();
        return calculationService.getResults(userContext, courseId);
    }
}
