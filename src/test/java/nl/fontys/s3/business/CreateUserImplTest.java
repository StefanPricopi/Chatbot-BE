//package nl.fontys.s3.business;
//
//import nl.fontys.s3.business.impl.CreateUserImpl;
//import nl.fontys.s3.domain.CreateUserRequest;
//import nl.fontys.s3.domain.CreateUserResponse;
//import nl.fontys.s3.persistence.UserRepository;
//import nl.fontys.s3.persistence.entity.UserEntity;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//public class CreateUserImplTest {
//    @Mock
//    private UserRepository userRepository;
//    private CreateUserImpl createUserImpl;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//        createUserImpl = new CreateUserImpl(userRepository);
//    }
//    @Test
//    public void testCreateUserImpl_Success(){
//        CreateUserRequest request = CreateUserRequest.builder()
//                .userName("John Doe")
//                .password("johndoe123")
//                .email("johndoe@gmail.com")
//                .role("CUSTOMER")
//                .build();
//        UserEntity newUser = UserEntity.builder()
//                .userId(1L)
//                .userName(request.getUserName())
//                .password(request.getPassword())
//                .email(request.getEmail())
//                .role(request.getRole())
//                .build();
//        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);
//
//        CreateUserResponse response = createUserImpl.createUser(request);
//
//        assertNotNull(response);
//        assertEquals(response.getUserId(), newUser.getUserId());
//    }
//}
