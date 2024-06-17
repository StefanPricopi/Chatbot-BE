package nl.fontys.s3.business;

import nl.fontys.s3.domain.users.CreateUserRequest;
import nl.fontys.s3.domain.users.CreateUserResponse;

public interface CreateUser {
    CreateUserResponse createUser(CreateUserRequest request);
}
