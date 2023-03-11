package pl.edu.agh.io.dzikizafrykibackend.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.io.dzikizafrykibackend.model.ImmutableHealthResource;

@Service
public class HealthService {

    public ImmutableHealthResource getIsHealthyInfo() {
        return ImmutableHealthResource.builder().setHealthy(true).build();
    }
}
