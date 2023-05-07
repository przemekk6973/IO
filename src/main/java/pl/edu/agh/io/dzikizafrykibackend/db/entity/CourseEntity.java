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
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_id")
    Set<User> students;

    @ManyToMany
    @JoinTable(
            name = "courses_dates",
            inverseJoinColumns = @JoinColumn(name = "date_id"),
            joinColumns = @JoinColumn(name = "course_id")
    )
    Set<DateEntity> dates;

    public CourseEntity(String name, String description, User teacher) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, teacher);
    }
}
