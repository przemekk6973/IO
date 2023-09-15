package pl.edu.agh.io.dzikizafrykibackend.db.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import pl.edu.agh.io.dzikizafrykibackend.db.jsonb.CalculationResults;

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
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
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

    @Column
    @NotNull
    private int groupCount;

    @Column
    private String comments;


    @Column
    @NotNull
    private boolean isCalculated = false;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private CalculationResults calculationResults;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrollments")
    Set<User> students;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", orphanRemoval = true, cascade = CascadeType.PERSIST)
    Set<DateEntity> dates;

    public CourseEntity(String name, String description, User teacher, Set<DateEntity> dates) {
        this.courseName = name;
        this.description = description;
        this.teacher = teacher;
        this.dates = dates;
    }

    public CourseEntity(
            UUID id, String courseName, String description, User teacher, Set<User> students, Set<DateEntity> dates
    ) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.teacher = teacher;
        this.students = students;
        this.dates = dates;
        this.comments = comments;

    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", name='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", isCalculated='" + isCalculated + '\'' +
                ", calculationResults='" + calculationResults + '\'' +
                ", teacher=" + teacher +
                ", students=" + students +
                ", dates=" + dates +
                ", comments=" + comments +
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
                Objects.equals(isCalculated, that.isCalculated) &&
                Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, description, isCalculated, teacher);
    }
}
