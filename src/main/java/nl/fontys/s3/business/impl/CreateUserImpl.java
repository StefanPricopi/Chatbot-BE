package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CreateUser;
import nl.fontys.s3.domain.CreateUserRequest;
import nl.fontys.s3.domain.CreateUserResponse;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserImpl implements CreateUser {
    private final UserRepository userRepository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request){
        UserEntity savedUser = saveNewUser(request);

        return CreateUserResponse.builder()
                .userId(savedUser.getUserId())
                .build();
    }


    private UserEntity saveNewUser (CreateUserRequest request){
        UserEntity newUser = UserEntity.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        return userRepository.save(newUser);
    }
}