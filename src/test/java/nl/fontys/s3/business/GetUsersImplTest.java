//package nl.fontys.s3.business;
//
//import nl.fontys.s3.business.impl.CreateUserImpl;
//import nl.fontys.s3.business.impl.GetUsersImpl;
//import nl.fontys.s3.domain.GetAllUsersResponse;
//import nl.fontys.s3.persistence.UserRepository;
//import nl.fontys.s3.persistence.entity.UserEntity;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//public class GetUsersImplTest {
//    @Mock
//    private UserRepository userRepository;
//    private GetUsersImpl getUsersImpl;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//        getUsersImpl = new GetUsersImpl(userRepository);
//    }
//
//    @Test
//    public void testGetUsersImpl_Success(){
//
//        UserEntity JohnDoe = UserEntity.builder()
//                .userId(1L)
//                .userName("John Doe")
//                .password("johndoe123")
//                .email("johndoe@gmail.com")
//                .role("CUSTOMER")
//                .build();
//        UserEntity JaneDoe = UserEntity.builder()
//                .userId(1L)
//                .userName("Jane Doe")
//                .password("janedoe123")
//                .email("janedoe@gmail.com")
//                .role("CUSTOMER")
//                .build();
//        UserEntity Admin = UserEntity.builder()
//                .userId(1L)
//                .userName("Admin")
//                .password("admin123")
//                .email("admin@gmail.com")
//                .role("ADMIN")
//                .build();
//
//        List<UserEntity> userList = new ArrayList<>(Arrays.asList(JohnDoe, JaneDoe, Admin));
//
//        when(userRepository.findAll()).thenReturn(userList);
//
//        GetAllUsersResponse response = getUsersImpl.getUsers();
//
//        assertNotNull(response);
//        assertEquals(userList.size(), response.getUsers().size());
//
//    }
//
//}
