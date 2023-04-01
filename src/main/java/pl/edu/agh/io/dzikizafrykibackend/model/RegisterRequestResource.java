package pl.edu.agh.io.dzikizafrykibackend.model;


import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserRole;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequestResource {

    @NonNull
    private String email;

    @NonNull
    private String firstname;

    @NonNull
    private String lastname;

    @NonNull
    private String password;

    @NonNull
    private UserRole role;

    @Nullable
    private int indexNumber;
}
