package ru.itis.trofimoff.todoapp.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.trofimoff.todoapp.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // finding by email
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE role = 'USER'", nativeQuery = true)
    List<User> findAllDefaultUsers();

    // sets confirmed parameter to true by code
    @Query("UPDATE User user SET user.confirmed = true WHERE user.confirmCode = :code")
    void confirmUser(@Param("code") String code);
}
