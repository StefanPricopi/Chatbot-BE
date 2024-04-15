package nl.fontys.s3.business;

import nl.fontys.s3.domain.UpdateUserRequest;
import nl.fontys.s3.persistence.entity.UserEntity;

public interface UpdateUser {
    void updateUser (UpdateUserRequest request);
}
