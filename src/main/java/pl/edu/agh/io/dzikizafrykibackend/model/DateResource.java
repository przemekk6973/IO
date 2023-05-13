package pl.edu.agh.io.dzikizafrykibackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.CourseEntity;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.DateEntity;
import pl.edu.agh.io.dzikizafrykibackend.util.WeekEnum;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class DateResource {

    @NotNull
    WeekEnum weekDay;

    @NotNull
    LocalTime startTime;

    @NotNull
    LocalTime endTime;

    public static DateResource fromEntity(DateEntity entity) {
        return DateResource.builder()
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
