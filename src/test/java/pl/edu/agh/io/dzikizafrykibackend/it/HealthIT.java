package pl.edu.agh.io.dzikizafrykibackend.it;

import org.junit.jupiter.api.Test;
import pl.edu.agh.io.dzikizafrykibackend.model.HealthResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthIT extends BaseIT {

    @Test
    void shouldReturnHealthyStatus() throws IOException {
        // when
        HealthResource health = retrofitClient().getHealth().execute().body();

        // then
        assertThat(health).isNotNull();
        assertThat(health.getHealthy()).isTrue();
        assertThat(health.getMessage()).isNotEmpty();
    }
}
