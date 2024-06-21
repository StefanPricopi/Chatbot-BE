package nl.fontys.s3.business;

import nl.fontys.s3.business.impl.CreateUserImpl;
import nl.fontys.s3.domain.users.CreateUserRequest;
import nl.fontys.s3.domain.users.CreateUserResponse;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreateUserImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private CreateUserImpl createUserImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        createUserImpl = new CreateUserImpl(userRepository, passwordEncoder);
    }
    @Test
    public void testCreateUserImpl_Success(){
        CreateUserRequest request = CreateUserRequest.builder()
                .username("John Doe")
                .password("johndoe123")
                .email("johndoe@gmail.com")
                .roles(Set.of("CUSTOMER"))
                .build();
        UserEntity newUser = UserEntity.builder()
                .userid(1L)
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .roles(request.getRoles().toString())
                .build();
        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);

        CreateUserResponse response = createUserImpl.createUser(request);

        assertNotNull(response);
        assertEquals(response.getUserId(), newUser.getUserid());
    }
}
