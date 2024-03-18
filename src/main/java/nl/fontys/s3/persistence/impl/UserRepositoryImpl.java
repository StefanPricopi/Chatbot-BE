package nl.fontys.s3.persistence.impl;

import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static long NEXT_ID = 1;

    private final List<UserEntity> savedUsers;

    public UserRepositoryImpl(){this.savedUsers = new ArrayList<>();    }

    @Override
    public UserEntity save(UserEntity user) {
        if (user.getUserId() == null) {
            user.setUserId(NEXT_ID);
            NEXT_ID++;
            this.savedUsers.add(user);
        }
        return user;
    }

    @Override
    public List<UserEntity> findAll(){
        return Collections.unmodifiableList(this.savedUsers);
    }

    @Override
    public void deleteByUserId(long userId){
        this.savedUsers.removeIf(userEntity -> userEntity.getUserId().equals(userId));
    }

    @Override
    public Optional<UserEntity> findById(long userId){
        return this.savedUsers.stream()
                .filter(userEntity -> userEntity.getUserId().equals(userId))
                .findFirst();
    }
}
