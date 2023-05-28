package pl.edu.agh.io.dzikizafrykibackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentPreferencesResource {

    @NotNull
    UUID courseId;

    @NotEmpty
    Set<UUID> datesIds;

    public static StudentPreferencesResource toDto(UUID courseId, Set<DateEntity> dates) {
        return StudentPreferencesResource.builder()
                .courseId(courseId)
                .datesIds(dates.stream().map(DateEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    public static StudentPreferencesResource toDto(UUID courseId, List<DateEntity> dates) {
        return toDto(courseId, new HashSet<>(dates));
    }
}
