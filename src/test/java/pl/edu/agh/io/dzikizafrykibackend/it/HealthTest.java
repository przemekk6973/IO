package pl.edu.agh.io.dzikizafrykibackend.it;

import org.junit.jupiter.api.Test;
import pl.edu.agh.io.dzikizafrykibackend.model.ImmutableHealthResource;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthTest extends BaseIT {

    private static final String HEALTH_ENDPOINT = "/health";

    @Test
    void shouldReturnHealthyStatus() {
        // when
        ImmutableHealthResource health = restTemplate.getForObject(HEALTH_ENDPOINT, ImmutableHealthResource.class);

        // then
        assertThat(health).isNotNull();
        assertThat(health.isHealthy()).isTrue();
        assertThat(health.getMessage()).isNotEmpty();
    }
}
