package pl.edu.agh.io.dzikizafrykibackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserPreferencesEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceResource {
    String studentEmail;
    Course course;
    Set<DateResource> dates;

    public static UserPreferencesEntity toEntity(User user, CourseEntity courseEntity, Set<DateEntity> dateEntities) {
        return UserPreferencesEntity.builder()
                .user(user)
                .preferredCourse(courseEntity)
                .preferredDates(dateEntities)
                .build();
    }

    public static UserPreferenceResource fromEntity(UserPreferencesEntity entity) {

        return UserPreferenceResource.builder()
                .course(Course.fromEntity(entity.getPreferredCourse()))
                .studentEmail(entity.getUser().getEmail())
                .dates(entity.getPreferredDates().stream().map(DateResource::fromEntity).collect(Collectors.toSet()))
                .build();
    }

}

