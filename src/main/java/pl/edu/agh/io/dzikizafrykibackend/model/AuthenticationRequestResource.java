package pl.edu.agh.io.dzikizafrykibackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationRequestResource {

    @NonNull
    public String email;

    @NonNull
    public String password;
}
