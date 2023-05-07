package pl.edu.agh.io.dzikizafrykibackend.db.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique=true)
    @NotNull
    private String email;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    @Column(unique=true)
    private Integer indexNumber;

    @Column
    @NotNull
    private Boolean isVerified;

    @Column
    @NotNull
    private String hashedPassword;

    // for teacher
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher", orphanRemoval = true)
    Set<CourseEntity> ownedCourses;

    // for students
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    Set<CourseEntity> assignedCourses;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "dates_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "date_id")
    )
    Set<DateEntity> userDates;

    public User(
            String email,
            String firstName,
            String lastName,
            UserRole role,
            Integer indexNumber,
            Boolean isVerified,
            String hashedPassword
    ) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.indexNumber = indexNumber;
        this.isVerified = isVerified;
        this.hashedPassword = hashedPassword;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Here a full role name is required, to force spring to not interpret it as an Authority
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", indexNumber=" + indexNumber +
                ", isVerified=" + isVerified +
                ", hashedPassword='" + hashedPassword + '\'' +
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
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                role == user.role &&
                Objects.equals(indexNumber, user.indexNumber) &&
                Objects.equals(isVerified, user.isVerified) &&
                Objects.equals(hashedPassword, user.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, role, indexNumber, isVerified, hashedPassword);
    }
}
