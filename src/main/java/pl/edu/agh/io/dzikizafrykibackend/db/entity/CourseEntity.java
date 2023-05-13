package pl.edu.agh.io.dzikizafrykibackend.db.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses_table")
public class CourseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotNull
    private String courseName;

    @Column
    @NotNull
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrollments")
    Set<User> students;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", orphanRemoval = true, cascade = CascadeType.PERSIST)
    Set<DateEntity> dates;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "preferredCourse", orphanRemoval = true, cascade = CascadeType.PERSIST)
    Set<UserPreferencesEntity> userPreferences;


    public CourseEntity(String name, String description, User teacher, Set<DateEntity> dates) {
        this.courseName = name;
        this.description = description;
        this.teacher = teacher;
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", name='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", teacher=" + teacher +
                ", students=" + students +
                ", dates=" + dates +
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
        CourseEntity that = (CourseEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, description, teacher);
    }
}
