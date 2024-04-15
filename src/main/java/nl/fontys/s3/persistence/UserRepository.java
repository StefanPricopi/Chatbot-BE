package nl.fontys.s3.persistence;

import nl.fontys.s3.domain.User;
import nl.fontys.s3.persistence.entity.UserEntity;
import java.util.*;
import java.util.List;

public interface UserRepository {
    UserEntity save(UserEntity user);
    List<UserEntity> findAll();
    Optional<UserEntity> findById(long userId);
    void deleteByUserId(long userId);
}
