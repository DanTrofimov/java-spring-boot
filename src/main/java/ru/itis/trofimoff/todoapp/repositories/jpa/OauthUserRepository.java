package ru.itis.trofimoff.todoapp.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.trofimoff.todoapp.models.OauthUser;

import java.util.Optional;

public interface OauthUserRepository extends JpaRepository<OauthUser, Integer> {
    Optional<OauthUser> getOauthUserByEmail(String email);
    Optional<OauthUser> findByServiceId(int serviceId);
}
