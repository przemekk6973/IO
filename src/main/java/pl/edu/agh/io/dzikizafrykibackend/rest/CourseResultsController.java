package pl.edu.agh.io.dzikizafrykibackend.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserRole;
import pl.edu.agh.io.dzikizafrykibackend.db.jsonb.CalculationResults;
import pl.edu.agh.io.dzikizafrykibackend.service.CalculationService;
import javax.servlet.http.HttpServletResponse;


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


    @GetMapping("/{courseId}/results")
    @Secured({UserRole.ROLE_STUDENT, UserRole.ROLE_TEACHER})
    public ResponseEntity<?> downloadResults(
            Authentication authentication,
            @PathVariable UUID courseId
    ) {
        User userContext = (User) authentication.getPrincipal();
        CalculationResults results = calculationService.getResults(userContext, courseId);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(results);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Ustaw nagłówek Content-Disposition (nazwa pliku)
            headers.setContentDispositionFormData("attachment", "results.json");

            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Błąd podczas generowania pliku JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
