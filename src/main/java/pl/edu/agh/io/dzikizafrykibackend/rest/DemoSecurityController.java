package pl.edu.agh.io.dzikizafrykibackend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserRole;

@RestController
@RequestMapping("/demo-security")
public class DemoSecurityController {

    @GetMapping(value = "/teacher-secured-endpoint", produces = "test/plain")
    @Secured({UserRole.ROLE_TEACHER})
    public ResponseEntity<String> getDemoTeacherSecuredHello(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.toString());
        return ResponseEntity.ok("Hello teacher!");
    }

    @GetMapping(value = "/student-secured-endpoint", produces = "text/plain")
    @Secured({UserRole.ROLE_STUDENT})
    public ResponseEntity<String> getDemoStudentSecuredHello(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.toString());
        return ResponseEntity.ok("Hello student!");
    }

    @GetMapping(value = "/not-secured-endpoint", produces = "text/plain")
    public ResponseEntity<String> getDemoNotSecurityHello() {
        return ResponseEntity.ok("Hello stranger!");
    }
}
