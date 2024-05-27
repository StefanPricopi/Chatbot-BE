package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.UpdateUser;
import nl.fontys.s3.domain.users.UpdateUserRequest;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserImpl implements UpdateUser {
    private final UserRepository userRepository;

    @Override
    public void updateUser(UpdateUserRequest request){
        Optional<UserEntity> userOptional = userRepository.findById(request.getUserId());
        UserEntity user = userOptional.get();
        updateFields(request, user);
    }

    private void updateFields(UpdateUserRequest request, UserEntity user){
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRolesSet(request.getRoles());
        userRepository.save(user);
    }
}
