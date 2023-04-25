package pl.edu.agh.io.dzikizafrykibackend.it.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Retrofit2ClientFactory {

    private final Retrofit.Builder retrofitBuilder;
    private String jwt = null;

    public Retrofit2ClientFactory(int port) {
        retrofitBuilder = new Retrofit.Builder()
                .baseUrl("http://localhost:" + port)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(
                        new ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                ));
    }

    public Retrofit2ClientFactory withAuthorization(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public RetrofitClient createClient() {
        if (jwt != null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + jwt)
                                .build();
                        return chain.proceed(request);
                    })
                    .build();
            return retrofitBuilder.client(client).build().create(RetrofitClient.class);
        }
        return retrofitBuilder.build().create(RetrofitClient.class);
    }
}
