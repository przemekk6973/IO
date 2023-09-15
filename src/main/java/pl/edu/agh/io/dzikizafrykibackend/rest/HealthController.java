package pl.edu.agh.io.dzikizafrykibackend.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.dzikizafrykibackend.model.HealthResource;
import pl.edu.agh.io.dzikizafrykibackend.service.HealthService;

@RestController
@RequestMapping("/health")
public class HealthController {

    HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public HealthResource getIsHealthyInfo() {
        return healthService.getIsHealthyInfo();
    }
}
