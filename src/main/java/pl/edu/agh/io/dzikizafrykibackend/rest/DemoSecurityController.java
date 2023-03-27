package pl.edu.agh.io.dzikizafrykibackend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-security")
public class DemoSecurityController {

    @GetMapping("/secured-endpoint")
    public ResponseEntity<String> getDemoSecurityHello() {
        return ResponseEntity.ok("Hello!");
    }

    @GetMapping("/not-secured-endpoint")
    public ResponseEntity<String> getDemoNotSecurityHello() {
        return ResponseEntity.ok("Hello!");
    }
}
