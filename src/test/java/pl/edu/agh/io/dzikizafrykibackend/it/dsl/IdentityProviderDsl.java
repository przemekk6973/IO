package pl.edu.agh.io.dzikizafrykibackend.it.dsl;

import pl.edu.agh.io.dzikizafrykibackend.db.repository.UserRepository;

public class IdentityProviderDsl {

    private final UserRepository userRepository;

    public IdentityProviderDsl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
