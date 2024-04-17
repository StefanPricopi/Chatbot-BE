package nl.fontys.s3.business;

import nl.fontys.s3.business.impl.GetUsersImpl;
import nl.fontys.s3.business.impl.UpdateUserImpl;
import nl.fontys.s3.domain.UpdateUserRequest;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateUserImplTest {
    @Mock
    private UserRepository userRepository;
    private UpdateUserImpl updateUserImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        updateUserImpl = new UpdateUserImpl(userRepository);
    }

    @Test
    public void testUpdateUserImpl_Success(){
        UserEntity oldUser = UserEntity.builder()
                .userId(5L)
                .userName("John Doe")
                .password("johndoe123")
                .email("johndoe@gmail.com")
                .role("ADMIN")
                .build();
        UpdateUserRequest request = UpdateUserRequest.builder()
                .userId(oldUser.getUserId())
                .userName("JaneDoe")
                .password("Janedoe123")
                .email("jane@gmail.com")
                .role("CUSTOMER")
                .build();

        UserEntity expectedUser = UserEntity.builder()
                    .userId(request.getUserId())
                    .userName(request.getUserName())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .role(request.getRole())
                    .build();

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(oldUser);

        updateUserImpl.updateUser(request);

        verify(userRepository, times(1)).findById(oldUser.getUserId());
        verify(userRepository, times(1)).save(oldUser);
        assertEquals(oldUser, expectedUser);
    }
}
