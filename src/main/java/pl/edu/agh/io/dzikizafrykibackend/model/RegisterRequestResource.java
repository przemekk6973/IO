package pl.edu.agh.io.dzikizafrykibackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestResource {

    private String email;

    private String firstname;

    private String lastname;

    private String password;

    private int indexNumber;
}
