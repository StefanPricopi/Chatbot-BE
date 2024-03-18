package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.UpdateUser;
import nl.fontys.s3.domain.UpdateUserRequest;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        userRepository.save(user);
    }
}
