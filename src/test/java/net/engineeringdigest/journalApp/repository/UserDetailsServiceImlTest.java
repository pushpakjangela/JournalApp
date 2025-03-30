package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserDetailsServiceIml;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
@ActiveProfiles("dev")
public class UserDetailsServiceImlTest {
    @InjectMocks
    private UserDetailsServiceIml userDetailsServiceIml;

    @Mock
    private UserRepository userRepository;

    private AutoCloseable autoCloseable;
    @BeforeEach
    void setUp() {
        autoCloseable =
                MockitoAnnotations.openMocks(this);
    }

//    @AfterEach
//    void tearDown() throws Exception {
//        if (autoCloseable != null) {
//            autoCloseable.close();
//        }
//    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("motu").password("inherit").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsServiceIml.loadUserByUsername("motu");
        Assertions.assertNotNull(user);


    }

}
