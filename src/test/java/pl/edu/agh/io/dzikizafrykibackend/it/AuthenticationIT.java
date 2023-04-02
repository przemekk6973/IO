package pl.edu.agh.io.dzikizafrykibackend.it;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserRole;
import pl.edu.agh.io.dzikizafrykibackend.model.AuthenticationRequestResource;
import pl.edu.agh.io.dzikizafrykibackend.model.AuthenticationResponseResource;
import pl.edu.agh.io.dzikizafrykibackend.model.RegisterRequestResource;

import java.io.IOException;

public class AuthenticationIT extends BaseIT {

    private final static String EMAIL = "example@example.com";
    private final static String PASSWORD = "strong_password123";

    @BeforeEach
    public void setup() {
        dsl().purgeEntities();
    }

    @Test
    void shouldRegisterAsTeacher() throws IOException {
        // given
        RegisterRequestResource registerRequestResource = RegisterRequestResource.builder()
                .firstname("Jan")
                .lastname("Kowalski")
                .indexNumber(null)
                .email(EMAIL)
                .password(PASSWORD)
                .role(UserRole.TEACHER)
                .build();

        // when
        AuthenticationResponseResource authenticationResponseResource =
                retrofitClient().postRegister(registerRequestResource).execute().body();

        // then
        Assertions.assertThat(authenticationResponseResource).isNotNull();
        Assertions.assertThat(authenticationResponseResource.jwt).isNotEmpty();

        User user = dsl().getUserRepository().findByEmail(EMAIL).orElse(null);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    void shouldAuthenticateAlreadyCreatedUser() throws IOException {
        // given
        RegisterRequestResource registerRequestResource = RegisterRequestResource.builder()
                .firstname("Jan")
                .lastname("Kowalski")
                .indexNumber(null)
                .email(EMAIL)
                .password(PASSWORD)
                .role(UserRole.TEACHER)
                .build();
        retrofitClient().postRegister(registerRequestResource).execute().body();

        // when
        AuthenticationRequestResource authenticationRequestResource = AuthenticationRequestResource.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        AuthenticationResponseResource authenticationResponseResource =
                retrofitClient().postAuthenticate(authenticationRequestResource).execute().body();

        Assertions.assertThat(authenticationResponseResource).isNotNull();
        Assertions.assertThat(authenticationResponseResource.jwt).isNotEmpty();

        User user = dsl().getUserRepository().findByEmail(EMAIL).orElse(null);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(EMAIL);
    }


}
