package pl.edu.agh.io.dzikizafrykibackend.db.entity;

import lombok.*;
import pl.edu.agh.io.dzikizafrykibackend.util.WeekEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dates")
public class DateEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "weekDay")
    @Enumerated(EnumType.STRING)
    @NotNull
    private WeekEnum weekDay;

    @Column(name = "startTime")
    @NotNull
    private LocalTime startTime;

    @Column(name = "endTime")
    @NotNull
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    CourseEntity course;

    @ManyToMany(mappedBy = "userDates")
    Set<User> users;

    public DateEntity(WeekEnum weekDay, LocalTime startTime, LocalTime endTime, CourseEntity course) {
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "id=" + id +
                ", weekDay=" + weekDay +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateEntity that = (DateEntity) o;
        return id == that.id &&
                weekDay == that.weekDay &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weekDay, startTime, endTime);
    }
}
