package pl.edu.agh.io.dzikizafrykibackend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserRole;

@RestController
@RequestMapping("/demo-security")
public class DemoSecurityController {

    @GetMapping("/teacher-secured-endpoint")
    @Secured({UserRole.ROLE_TEACHER})
    public ResponseEntity<String> getDemoTeacherSecuredHello() {
        return ResponseEntity.ok("Hello teacher!");
    }

    @GetMapping("/student-secured-endpoint")
    @Secured({UserRole.ROLE_STUDENT})
    public ResponseEntity<String> getDemoStudentSecuredHello() {
        return ResponseEntity.ok("Hello student!");
    }

    @GetMapping("/not-secured-endpoint")
    public ResponseEntity<String> getDemoNotSecurityHello() {
        return ResponseEntity.ok("Hello stranger!");
    }
}
