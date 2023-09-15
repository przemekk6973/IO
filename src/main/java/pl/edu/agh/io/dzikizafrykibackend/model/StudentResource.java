package pl.edu.agh.io.dzikizafrykibackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResource {
    UUID studentId;
    String email;

    public static StudentResource fromEntity(User user) {
        return StudentResource.builder()
                .studentId(user.getId())
                .email(user.getEmail())
                .build();
    }
}
