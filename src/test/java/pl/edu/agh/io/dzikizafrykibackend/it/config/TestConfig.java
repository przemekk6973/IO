package pl.edu.agh.io.dzikizafrykibackend.it.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.edu.agh.io.dzikizafrykibackend.it.client.Retrofit2ClientFactory;
import pl.edu.agh.io.dzikizafrykibackend.it.client.RetrofitClient;

@Configuration
public class TestConfig {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public RetrofitClient retrofitClient(@Value("${local.server.port}") int port) {
        return new Retrofit2ClientFactory(port).createClient();
    }
}
