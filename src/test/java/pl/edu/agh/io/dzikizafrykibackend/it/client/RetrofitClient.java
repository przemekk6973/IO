package pl.edu.agh.io.dzikizafrykibackend.it.client;

import pl.edu.agh.io.dzikizafrykibackend.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface RetrofitClient {

    @GET("/health")
    Call<HealthResource> getHealth();

    @POST("/auth/register")
    Call<AuthenticationResponseResource> postRegister(@Body RegisterRequestResource registerRequestResource);

    @POST("/auth/authenticate")
    Call<AuthenticationResponseResource> postAuthenticate(@Body AuthenticationRequestResource authenticationRequestResource);

    @GET("/demo-security/teacher-secured-endpoint")
    Call<String> getDemoTeacherSecuredHello();

    @GET("/demo-security/student-secured-endpoint")
    Call<String> getDemoStudentSecuredHello();

    @GET("/course")
    Call<List<CourseResource>> getOwnedCourses();

    @GET("/course/{courseId}")
    Call<CourseResource> getCourse(@Path("courseId") UUID courseId);

    @POST("/course/create")
    Call<CourseResource> postCourse(@Body CourseCreationResource courseCreationResource);

    @DELETE("course/{courseId}")
    Call<Void> deleteCourse(@Path("courseId") UUID courseId);

    @POST("course-enrollment/enroll/{courseId}")
    Call<CourseResource> postEnroll(@Path("courseId") UUID courseId);
}
