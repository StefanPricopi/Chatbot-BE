package nl.fontys.s3.business;

import nl.fontys.s3.business.impl.GetUsersImpl;
import nl.fontys.s3.business.impl.UpdateUserImpl;
import nl.fontys.s3.domain.users.UpdateUserRequest;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

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
                .userid(5L)
                .username("John Doe")
                .password("johndoe123")
                .email("johndoe@gmail.com")
                .roles("ADMIN")
                .build();
        UpdateUserRequest request = UpdateUserRequest.builder()
                .userId(oldUser.getUserid())
                .username("JaneDoe")
                .password("Janedoe123")
                .email("jane@gmail.com")
                .roles(Set.of("CUSTOMER"))
                .build();

        UserEntity expectedUser = UserEntity.builder()
                    .userid(request.getUserId())
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .roles(request.getRoles().toString())
                    .build();

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(oldUser);

        updateUserImpl.updateUser(request);

        verify(userRepository, times(1)).findById(oldUser.getUserid());
        verify(userRepository, times(1)).save(oldUser);
        assertEquals(oldUser, expectedUser);
    }
}
