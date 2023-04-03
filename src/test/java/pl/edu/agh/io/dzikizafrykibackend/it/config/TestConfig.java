package pl.edu.agh.io.dzikizafrykibackend.it.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.edu.agh.io.dzikizafrykibackend.it.client.RetrofitClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class TestConfig {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public RetrofitClient retrofitClient(@Value("${local.server.port}") int port) {
        return new Retrofit.Builder().baseUrl("http://localhost:" + port).addConverterFactory(
                JacksonConverterFactory.create(
                        new ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false))
                )
                .build().create(RetrofitClient.class);
    }
}
