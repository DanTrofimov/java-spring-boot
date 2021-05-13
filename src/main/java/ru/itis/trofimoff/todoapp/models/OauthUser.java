package ru.itis.trofimoff.todoapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "oauth_users")
public class OauthUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private int serviceId;
    private String name;
    private String soname;
    private String email;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
