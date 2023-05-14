package pl.edu.agh.io.dzikizafrykibackend.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "preferences_table")
public class UserPreferencesEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "preferences_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "preferences_course", nullable = false)
    private CourseEntity preferredCourse;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_preferred_dates",
            joinColumns = @JoinColumn(name = "preferences_id"),
            inverseJoinColumns = @JoinColumn(name = "date_id"))
    private Set<DateEntity> preferredDates;

}
