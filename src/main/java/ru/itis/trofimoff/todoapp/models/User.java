package ru.itis.trofimoff.todoapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.trofimoff.todoapp.dto.SignInFormDto;
import ru.itis.trofimoff.todoapp.dto.SignUpFormDto;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

// JPA
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private int allTodos;
    private int doneTodos;

    private Boolean confirmed;
    private String confirmCode;

    @ManyToMany
    @JoinTable(name = "users_todos",
            joinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "todos_id", referencedColumnName = "id")})
    private List<Todo> todos;

    @Enumerated(value = EnumType.STRING)
    private State state = State.ACTIVE;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    @Enumerated(value = EnumType.STRING)
    private Type type = Type.DEFAULT;

    public enum State {
        ACTIVE, BANNED
    }

    public enum Role {
        USER, ADMIN
    }

    public enum Type {
        VK, DEFAULT
    }

    // safe
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, int allTodos, int doneTodos) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.allTodos = allTodos;
        this.doneTodos = doneTodos;
    }

    public User(SignInFormDto form) {
        this.email = form.getEmail();
        this.password = form.getPassword();
    }

    public User(SignUpFormDto form) {
        this.name = form.getName();
        this.email = form.getEmail();
        this.password = form.getPassword();
    }

    public User(OauthUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = "stub";
    }

    public boolean isActive() {
        return this.state == State.ACTIVE;
    }

    public boolean isBanned() {
        return this.state == State.BANNED;
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }
}
