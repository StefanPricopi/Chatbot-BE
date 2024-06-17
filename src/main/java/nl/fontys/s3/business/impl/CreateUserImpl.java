package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CreateUser;
import nl.fontys.s3.business.exceptions.EmailAlreadyTakenException;
import nl.fontys.s3.business.exceptions.UserNameAlreadyTakenException;
import nl.fontys.s3.business.exceptions.UserNotFoundException;
import nl.fontys.s3.domain.users.CreateUserRequest;
import nl.fontys.s3.domain.users.CreateUserResponse;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateUserImpl implements CreateUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request){

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserNameAlreadyTakenException();
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException();
        }

        UserEntity savedUser = saveNewUser(request);
        return CreateUserResponse.builder()
                .userId(savedUser.getUserid())
                .build();
    }


    private UserEntity saveNewUser (CreateUserRequest request){

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .build();
        newUser.setRolesSet(request.getRoles());
        return userRepository.save(newUser);
    }
}