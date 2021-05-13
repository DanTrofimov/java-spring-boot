package ru.itis.trofimoff.todoapp.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.trofimoff.todoapp.models.User;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    // finding by email
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE role = 'USER'", nativeQuery = true)
    List<User> findAllDefaultUsers();

    // sets confirmed parameter to true by code
    @Query("UPDATE User user SET user.confirmed = true WHERE user.confirmCode = :code")
    void confirmUser(@Param("code") String code);

    // changing user's stat - all
    @Modifying
    @Query(value = "UPDATE users SET all_todos = all_todos + 1 WHERE id = ?1", nativeQuery =  true)
    void incrementUserStatAll(int id);

    // changing user's stat - done
    @Modifying
    @Query(value = "UPDATE users SET done_todos = done_todos + 1 WHERE id = ?1", nativeQuery = true)
    void incrementUserStatDone(int id);
}
