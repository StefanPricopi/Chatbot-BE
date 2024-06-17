package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.UpdateUser;
import nl.fontys.s3.business.exceptions.EmailAlreadyTakenException;
import nl.fontys.s3.business.exceptions.UserNameAlreadyTakenException;
import nl.fontys.s3.domain.users.UpdateUserRequest;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserImpl implements UpdateUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUser(UpdateUserRequest request){
        Optional<UserEntity> user1 = userRepository.findByUsername(request.getUsername());
        if(user1.isPresent() && request.getUserid() != user1.get().getUserid())
        {
            throw new UserNameAlreadyTakenException();
        }

        Optional<UserEntity> user2 = userRepository.findByEmail(request.getEmail());
        if(user2.isPresent() && request.getUserid() != user2.get().getUserid()){
            throw new EmailAlreadyTakenException();
        }

        Optional<UserEntity> userOptional = userRepository.findById(request.getUserid());
        UserEntity user = userOptional.get();
        updateFields(request, user);
    }

    private void updateFields(UpdateUserRequest request, UserEntity user){
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(request.getEmail());
        user.setRolesSet(request.getRoles());
        userRepository.save(user);
    }
}
