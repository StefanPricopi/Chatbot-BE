package nl.fontys.s3.persistence;

import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
