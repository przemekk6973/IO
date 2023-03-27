package pl.edu.agh.io.dzikizafrykibackend.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.io.dzikizafrykibackend.model.HealthResource;

@Service
public class HealthService {

    public HealthResource getIsHealthyInfo() {
        return HealthResource.builder().healthy(true).message("Backend service is healthy and running!").build();
    }
}
