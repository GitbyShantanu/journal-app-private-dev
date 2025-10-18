package net.engineeringdigest.journalApp.serviceTests;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@Disabled
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Mock repository, real DB nahi use hoga

    @InjectMocks
    private UserService userService; // Mockito automatically mock repository inject karega

    @Test
    void testSaveNewUser_ShouldEncodePasswordAndAssignRoles() {
        // Prepare test data
        User user = new User();
        user.setUserName("Pakya");
        user.setPassword("myPass");

        // Simple stub: jab kisi bhi user ki save() call ho, wahi user return ho jaaye
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act: service method call
        userService.saveNewUser(user);

        // Assert: verify username, encoded password, and roles
        assertEquals("Pakya", user.getUserName());           // username correct set hua?
        assertNotEquals("myPass", user.getPassword());     // password encode hua?
        assertNotNull(user.getRoles());                      // roles null nahi?
        assertEquals(1, user.getRoles().size());    // sirf 1 role assign hua?
        assertTrue(user.getRoles().contains("USER"));        // USER role properly assign hua?
    }
}


