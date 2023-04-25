package pl.edu.agh.io.dzikizafrykibackend.it.client;

import pl.edu.agh.io.dzikizafrykibackend.model.AuthenticationRequestResource;
import pl.edu.agh.io.dzikizafrykibackend.model.AuthenticationResponseResource;
import pl.edu.agh.io.dzikizafrykibackend.model.HealthResource;
import pl.edu.agh.io.dzikizafrykibackend.model.RegisterRequestResource;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
}
