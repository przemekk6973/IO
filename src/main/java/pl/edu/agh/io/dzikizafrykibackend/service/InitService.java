package pl.edu.agh.io.dzikizafrykibackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.ExampleEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.repository.ExampleRepository;

import java.util.List;

@Service
public class InitService {

    ExampleRepository exampleRepository;

    @Autowired
    public InitService(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("Running simple database request...");
        exampleRepository.save(new ExampleEntity("My dummy text"));
        List<ExampleEntity> exampleEntities = exampleRepository.findAll();
        exampleEntities.forEach(e -> System.out.println(e.getExampleColumn()));
    }
}
