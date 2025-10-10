package net.engineeringdigest.journalApp.serviceTest;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@SpringBootTest // Loads full Application context for testing (Integration-style test)
public class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService; // Actual service under test, auto-wired from context

    @MockBean
    UserRepository userRepository; // Mocked bean inserted into Spring context (replaces real repository)

    @Test
    void loadUserByUsernameTest() {

        // Mocking repository behavior: whenever findByUserName() is called with any String,
        // return a dummy User entity built using Lombok's @Builder
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .userName("ram")
                        .password("sdawkkjas")
                        .roles(new ArrayList<>())
                        .build());

        // Calling the actual service method to test its logic
        UserDetails user = userDetailsService.loadUserByUsername("Ram");

        // Assertion to verify that the service successfully returned a UserDetails object
        Assertions.assertNotNull(user);
    }

}
