package nl.fontys.s3.business.impl;

import lombok.NoArgsConstructor;
import nl.fontys.s3.domain.users.User;
import nl.fontys.s3.persistence.entity.UserEntity;

@NoArgsConstructor
public class UserConverter {
    public static User convert(UserEntity user){
        return User.builder()
                .userId(user.getUserid())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRolesSet())
                .build();
    }
}
