package pl.edu.agh.io.dzikizafrykibackend.it.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestClient {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public RestTemplate getRestClient(@Value("${local.server.port}") int port) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(
                new DefaultUriBuilderFactory("http://localhost:" + port));
        return restTemplate;
    }
}
