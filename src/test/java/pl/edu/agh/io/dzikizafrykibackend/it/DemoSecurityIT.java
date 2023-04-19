package pl.edu.agh.io.dzikizafrykibackend.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DemoSecurityIT extends BaseIT {

    @BeforeEach
    public void setup() throws IOException {
        dsl().purgeEntities();
        dsl().setupUser(STUDENT1_ID);
        dsl().setupUser(TEACHER1_ID);
    }

    @Test
    void authenticatedStudent() throws IOException {
        // when
        String res = dsl().useClientAsUser(STUDENT1_ID).getDemoStudentSecuredHello().execute().body();

        // then
        assertThat(res).isEqualTo("Hello student!");
    }

    @Test
    void authenticatedTeacher() throws IOException {
        // when
        String res = dsl().useClientAsUser(TEACHER1_ID).getDemoTeacherSecuredHello().execute().body();

        // then
        assertThat(res).isEqualTo("Hello teacher!");
    }
}
