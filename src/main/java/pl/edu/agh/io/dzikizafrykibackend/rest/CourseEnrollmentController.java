package pl.edu.agh.io.dzikizafrykibackend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.dzikizafrykibackend.service.CourseEnrollmentService;

@RestController
@RequestMapping("/course-enrollment")
public class CourseEnrollmentController {

    private final CourseEnrollmentService courseEnrollmentService;

    @Autowired
    public CourseEnrollmentController(CourseEnrollmentService courseEnrollmentService) {
        this.courseEnrollmentService = courseEnrollmentService;
    }


}
