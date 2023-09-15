package pl.edu.agh.io.dzikizafrykibackend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.dzikizafrykibackend.model.AuthenticationRequestResource;
import pl.edu.agh.io.dzikizafrykibackend.model.AuthenticationResponseResource;
import pl.edu.agh.io.dzikizafrykibackend.model.RegisterRequestResource;
import pl.edu.agh.io.dzikizafrykibackend.service.AuthenticationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    private AuthenticationResponseResource register(
            @RequestBody @Valid RegisterRequestResource registerRequestResource
    ) {
        return authenticationService.register(registerRequestResource);
    }

    @PostMapping("/authenticate")
    private AuthenticationResponseResource authenticate(
            @RequestBody AuthenticationRequestResource authenticationRequestResource
    ) {
        return authenticationService.authenticate(authenticationRequestResource);
    }
}
