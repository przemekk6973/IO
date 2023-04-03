package pl.edu.agh.io.dzikizafrykibackend.db.entity;

public enum UserRole {

    TEACHER,
    STUDENT;

    public static final String ROLE_TEACHER = "ROLE_TEACHER";
    public static final String ROLE_STUDENT = "ROLE_STUDENT";

    public String getRoleName() {
        return switch (this) {
            case TEACHER -> ROLE_TEACHER;
            case STUDENT -> ROLE_STUDENT;
        };
    }

    public static UserRole getEnum(String role) {
        return switch (role) {
            case ROLE_TEACHER -> TEACHER;
            case ROLE_STUDENT -> STUDENT;
            default -> throw new IllegalStateException("Unexpected value: " + role);
        };
    }
}
