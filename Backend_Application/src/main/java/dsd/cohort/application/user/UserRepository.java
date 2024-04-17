package dsd.cohort.application.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is a simple implement of the users repository that uses JPA
 * and has no frills or logic.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT u FROM UserEntity u WHERE u.email=:email")
    Optional<UserEntity> findByEmail(@Param("email") String email);
}
