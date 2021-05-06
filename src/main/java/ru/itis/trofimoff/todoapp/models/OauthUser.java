package ru.itis.trofimoff.todoapp.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder

@Entity
@Table(name = "oauth_users")
public class OauthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String soname;
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
