package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CreateUser;
import nl.fontys.s3.domain.users.CreateUserRequest;
import nl.fontys.s3.domain.users.CreateUserResponse;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CreateUserImpl implements CreateUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request){
        UserEntity savedUser = saveNewUser(request);

        return CreateUserResponse.builder()
                .userId(savedUser.getUserId())
                .build();
    }


    private UserEntity saveNewUser (CreateUserRequest request){

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Set<String> roles = new HashSet<>();
        roles.add("CUSTOMER");

        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
//                .role(request.getRole())      //Usually this should be request.getRole() and the role should be set when creating account but for testing purposes, the standard role is CUSTOMER.
                .roles(roles)
                .build();
        return userRepository.save(newUser);
    }
}