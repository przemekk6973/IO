package pl.edu.agh.io.dzikizafrykibackend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserRole;
import pl.edu.agh.io.dzikizafrykibackend.model.CourseResource;
import pl.edu.agh.io.dzikizafrykibackend.model.StudentPreferencesResource;
import pl.edu.agh.io.dzikizafrykibackend.service.CourseEnrollmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course-enrollment")
public class CourseEnrollmentController {

    private final CourseEnrollmentService courseEnrollmentService;

    @Autowired
    public CourseEnrollmentController(CourseEnrollmentService courseEnrollmentService) {
        this.courseEnrollmentService = courseEnrollmentService;
    }

    @PostMapping("/enroll/{courseId}")
    @Secured({UserRole.ROLE_STUDENT})
    public CourseResource getCourse(Authentication authentication, @PathVariable UUID courseId) {
        User userContext = (User) authentication.getPrincipal();
        return courseEnrollmentService.enrollStudent(userContext, courseId);
    }

    @PostMapping("/preferences")
    @Secured({UserRole.ROLE_STUDENT})
    public StudentPreferencesResource postPreferences(
            Authentication authentication,
            @RequestBody StudentPreferencesResource studentPreferencesResource
    ) {
        User userContext = (User) authentication.getPrincipal();
        return courseEnrollmentService.saveStudentPreferences(userContext, studentPreferencesResource);
    }

    @GetMapping("/preferences/all")
    @Secured({UserRole.ROLE_STUDENT})
    public List<StudentPreferencesResource> getAllPreferences(Authentication authentication) {
        User userContext = (User) authentication.getPrincipal();
        return courseEnrollmentService.getAllPreferences(userContext);
    }
}
