package pl.edu.agh.io.dzikizafrykibackend.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.ExampleEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.ExampleRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class DbTest extends BaseIT {

    @Autowired
    ExampleRepository exampleRepository;

    @BeforeEach
    public void setup() {
        exampleRepository.deleteAll();
    }

    @Test
    void shouldReturnHealthyStatus() {
        // given
        String str = "Hello World";
        exampleRepository.save(ExampleEntity.builder().exampleColumn(str).build());

        // when
        ExampleEntity entity = exampleRepository.findAll().get(0);

        // then
        assertThat(entity.getExampleColumn()).isEqualTo(str);
    }
}
