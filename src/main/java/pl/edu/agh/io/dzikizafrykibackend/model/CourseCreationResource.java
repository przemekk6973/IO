package pl.edu.agh.io.dzikizafrykibackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreationResource {

    @Size(min = 3, max = 30, message = "Wrong name.")
    String name;

    @Size(min = 3, max = 150)
    String description;

    @NotEmpty
    @NotNull
    Set<DateResource> dates;
}
