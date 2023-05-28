package pl.edu.agh.io.dzikizafrykibackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResource {
    UUID courseId;
    String name;
    String description;
    Boolean isCalculated;
    String teacher;
    Set<StudentResource> students;
    Set<DateResource> dates;


    public static CourseResource fromEntity(CourseEntity entity) {
        return CourseResource.builder()
                .courseId(entity.getId())
                .name(entity.getCourseName())
                .description(entity.getDescription())
                .isCalculated(entity.isCalculated())
                .teacher(entity.getTeacher().getEmail())
                .students(entity.getStudents() == null ? Set.of() : entity.getStudents()
                        .stream()
                        .map(StudentResource::fromEntity)
                        .collect(Collectors.toSet()))
                .dates(entity.getDates().stream().map(DateResource::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
