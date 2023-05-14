package pl.edu.agh.io.dzikizafrykibackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollResource {

    @NotNull
    UUID courseId;

    @NotEmpty
    @NotNull
    Set<DateResource> dates;

//    @Size(max = 200, message = "Max 200 characters")
//    String studentComment;

}
