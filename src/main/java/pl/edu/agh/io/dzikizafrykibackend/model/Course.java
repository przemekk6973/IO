package pl.edu.agh.io.dzikizafrykibackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    UUID courseId;
    String name;
    String description;
    String teacher;
    Set<String> students;
    Set<DateResource> dates;


    public static Course fromEntity(CourseEntity entity) {
        return Course.builder()
                .courseId(entity.getId())
                .name(entity.getCourseName())
                .description(entity.getDescription())
                .teacher(entity.getTeacher().getEmail())
                .students(entity.getStudents() == null ? Set.of() : entity.getStudents()
                        .stream()
                        .map(User::getEmail)
                        .collect(Collectors.toSet()))
//                .students(entity.getUsers().stream()
//                               .map(e -> e.getFirstName() + " " + e.getLastName())
//                               .collect(Collectors.toSet()))
                .dates(entity.getDates().stream().map(DateResource::fromEntity).collect(Collectors.toSet()))
                .build();
    }

}
