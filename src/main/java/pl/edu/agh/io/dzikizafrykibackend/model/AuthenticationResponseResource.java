package pl.edu.agh.io.dzikizafrykibackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponseResource {

    public String jwt;
}
