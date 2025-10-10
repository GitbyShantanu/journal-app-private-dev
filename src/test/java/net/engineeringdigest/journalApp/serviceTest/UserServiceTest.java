package net.engineeringdigest.journalApp.serviceTest;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSaveNewUser_ShouldEncodePasswordAndAssignRoles() {
        // Act
        User user = new User();
        user.setUserName("Pakya");
        user.setPassword("myPass");

        // Action
        userService.saveNewUser(user);

        // Assert
        assertEquals("Pakya", user.getUserName());         // username sahi set hua ?
        assertNotEquals("myPass", user.getPassword());   // password encode hua ?
        assertNotNull(user.getRoles());                         // roles null nahi ?
        assertEquals(1, user.getRoles().size());       // sirf 1 role ?
        assertTrue(user.getRoles().contains("USER"));           // user role assigned
    }

}
