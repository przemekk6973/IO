package pl.edu.agh.io.dzikizafrykibackend.it;

import org.junit.jupiter.api.Test;
import pl.edu.agh.io.dzikizafrykibackend.model.HealthResource;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthTest extends BaseIT {

    private static final String HEALTH_ENDPOINT = "/health";

    @Test
    void shouldReturnHealthyStatus() {
        // when
        HealthResource health = restTemplate.getForObject(HEALTH_ENDPOINT, HealthResource.class);

        // then
        assertThat(health).isNotNull();
        assertThat(health.getHealthy()).isTrue();
        assertThat(health.getMessage()).isNotEmpty();
    }
}
