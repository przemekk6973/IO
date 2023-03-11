package pl.edu.agh.io.dzikizafrykibackend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@DtoStyle
@JsonSerialize
@JsonDeserialize
public abstract class AbstractHealthResource {

    public abstract Boolean isHealthy();

    @Nullable
    @Value.Default
    public String getMessage() {
        return "Backend service is healthy and running!";
    }
}
