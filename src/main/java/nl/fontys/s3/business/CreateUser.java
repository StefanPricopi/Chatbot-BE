package nl.fontys.s3.business;

import nl.fontys.s3.domain.CreateUserRequest;
import nl.fontys.s3.domain.CreateUserResponse;

public interface CreateUser {
    CreateUserResponse createUser(CreateUserRequest request);
}
