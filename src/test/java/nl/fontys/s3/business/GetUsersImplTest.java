package nl.fontys.s3.business;

import nl.fontys.s3.business.impl.GetUsersImpl;
import nl.fontys.s3.domain.users.GetAllUsersResponse;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GetUsersImplTest {
    @Mock
    private UserRepository userRepository;
    private GetUsersImpl getUsersImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        getUsersImpl = new GetUsersImpl(userRepository);
    }

    @Test
    public void testGetUsersImpl_Success(){

        UserEntity JohnDoe = UserEntity.builder()
                .userid(1L)
                .username("John Doe")
                .password("johndoe123")
                .email("johndoe@gmail.com")
                .roles("CUSTOMER")
                .build();
        UserEntity JaneDoe = UserEntity.builder()
                .userid(1L)
                .username("Jane Doe")
                .password("janedoe123")
                .email("janedoe@gmail.com")
                .roles("CUSTOMER")
                .build();
        UserEntity Admin = UserEntity.builder()
                .userid(1L)
                .username("Admin")
                .password("admin123")
                .email("admin@gmail.com")
                .roles("ADMIN")
                .build();

        List<UserEntity> userList = new ArrayList<>(Arrays.asList(JohnDoe, JaneDoe, Admin));

        when(userRepository.findAll()).thenReturn(userList);

        GetAllUsersResponse response = getUsersImpl.getUsers();

        assertNotNull(response);
        assertEquals(userList.size(), response.getUsers().size());

    }

}
