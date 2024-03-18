package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.GetUsers;
import nl.fontys.s3.domain.GetAllUsersResponse;
import nl.fontys.s3.domain.User;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersImpl implements GetUsers {
    private UserRepository userRepository;

    @Override
    public GetAllUsersResponse getUsers(){
        List<UserEntity> results;
        results = userRepository.findAll();

        final GetAllUsersResponse response = new GetAllUsersResponse();
        List<User> users = results
                .stream()
                .map(UserConverter::convert)
                .toList();
        response.setUsers(users);

        return response;
    }
}
