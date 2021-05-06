package ru.itis.trofimoff.todoapp.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.trofimoff.todoapp.models.OauthUser;

public interface OauthUserRepository extends JpaRepository<Integer, OauthUser> {
}
