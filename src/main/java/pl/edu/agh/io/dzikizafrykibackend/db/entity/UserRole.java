package pl.edu.agh.io.dzikizafrykibackend.db.entity;

public enum UserRole {

    TEACHER,
    STUDENT;

    public static final String ROLE_TEACHER = "ROLE_TEACHER";
    public static final String ROLE_STUDENT = "ROLE_STUDENT";

    public String getFullRoleName() {
        return "ROLE_" + this.name();
    }

}
