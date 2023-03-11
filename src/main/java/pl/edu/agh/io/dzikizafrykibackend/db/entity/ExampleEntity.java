package pl.edu.agh.io.dzikizafrykibackend.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "example_table")
public class ExampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "example_column")
    private String exampleColumn;

    public ExampleEntity() {

    }

    public ExampleEntity(String exampleColumn) {
        this.exampleColumn = exampleColumn;
    }

    public int getId() {
        return id;
    }

    public String getExampleColumn() {
        return exampleColumn;
    }

    public void setExampleColumn(String exampleColumn) {
        this.exampleColumn = exampleColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExampleEntity that = (ExampleEntity) o;
        return id == that.id && Objects.equals(exampleColumn, that.exampleColumn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exampleColumn);
    }
}
