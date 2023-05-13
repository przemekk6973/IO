package pl.edu.agh.io.dzikizafrykibackend.it.dsl;


import pl.edu.agh.io.dzikizafrykibackend.db.repository.CourseRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.DateRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.UserPreferencesRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.UserRepository;
import pl.edu.agh.io.dzikizafrykibackend.it.client.Retrofit2ClientFactory;
import pl.edu.agh.io.dzikizafrykibackend.it.client.RetrofitClient;

import java.io.IOException;

public class TestDsl {

    private final int port;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final DateRepository dateRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final IdentityProviderDsl identityProvider;

    public TestDsl(int port, UserRepository userRepository, CourseRepository courseRepository, DateRepository dateRepository, UserPreferencesRepository userPreferencesRepository) {
        this.port = port;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.dateRepository = dateRepository;
        this.userPreferencesRepository = userPreferencesRepository;

        this.identityProvider = new IdentityProviderDsl(
                userRepository,
                courseRepository,
                userPreferencesRepository,
                new Retrofit2ClientFactory(port).createClient()
        );
    }

    public void setupUser(IdentityProviderDsl.UserIdentity userIdentity) throws IOException {
        identityProvider.register(userIdentity);
    }

    public RetrofitClient useClientAsUser(IdentityProviderDsl.UserIdentity userIdentity) throws IOException {
        String jwt = identityProvider.authenticate(userIdentity);
        return new Retrofit2ClientFactory(port).withAuthorization(jwt).createClient();
    }

    public void purgeEntities() {
        userRepository.deleteAll();
        courseRepository.deleteAll();
        dateRepository.deleteAll();
        userPreferencesRepository.deleteAll();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public CourseRepository getCourseRepository() {
        return courseRepository;
    }

    public DateRepository getDateRepository() {
        return dateRepository;
    }

    public UserPreferencesRepository getUserPreferencesRepository() { return userPreferencesRepository; }

    public IdentityProviderDsl getIdentityProvider() {
        return identityProvider;
    }
}
