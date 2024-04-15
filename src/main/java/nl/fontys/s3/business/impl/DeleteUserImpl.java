package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.DeleteUser;
import nl.fontys.s3.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserImpl implements DeleteUser {
    private final UserRepository userRepository;

    @Override
    public void deleteUser(long userId) {this.userRepository.deleteByUserId(userId);}
}
