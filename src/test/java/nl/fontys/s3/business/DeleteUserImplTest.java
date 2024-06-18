package nl.fontys.s3.business;

import nl.fontys.s3.business.impl.DeleteUserImpl;
import nl.fontys.s3.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DeleteUserImplTest {
    @Mock
    private UserRepository userRepository;
    private DeleteUserImpl deleteUserImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        deleteUserImpl = new DeleteUserImpl(userRepository);
    }

    @Test
    public void testDeleteUserImpl_Success(){

        long targetId = 3L;

        deleteUserImpl.deleteUser(targetId);

        verify(userRepository, times(1)).deleteById(targetId);
        assertEquals(userRepository.findById(targetId), Optional.empty());
    }
}
