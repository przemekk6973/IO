package pl.edu.agh.io.dzikizafrykibackend.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.util.WeekEnum;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class DateResource {

    @Nullable
    @EqualsAndHashCode.Exclude
    UUID dateId;

    @NotNull
    WeekEnum weekDay;

    @NotNull
    LocalTime startTime;

    @NotNull
    LocalTime endTime;

    public DateResource(WeekEnum weekDay, LocalTime startTime, LocalTime endTime) {
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static DateResource fromEntity(DateEntity entity) {
        return DateResource.builder()
                .dateId(entity.getId())
                .weekDay(entity.getWeekDay())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }

    public static DateEntity toEntity(DateResource dateResource, CourseEntity course) {
        return DateEntity.builder()
                .weekDay(dateResource.getWeekDay())
                .startTime(dateResource.getStartTime())
                .endTime(dateResource.getEndTime())
                .course(course)
                .build();
    }
}
