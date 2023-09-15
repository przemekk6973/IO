package pl.edu.agh.io.dzikizafrykibackend.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class InitService {

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("Service is running...");
    }
}
