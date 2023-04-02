package pl.edu.agh.io.dzikizafrykibackend.it.dsl;

import pl.edu.agh.io.dzikizafrykibackend.db.repository.UserRepository;

public class TestDsl {

    private final UserRepository userRepository;

    private final IdentityProviderDsl identityProvider;

    public TestDsl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.identityProvider = new IdentityProviderDsl(userRepository);
    }

    public void purgeEntities() {
        this.userRepository.deleteAll();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
