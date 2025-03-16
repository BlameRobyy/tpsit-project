import org.junit.Test;
import static org.junit.Assert.*;

public class AccTest {

    @Test
    public void testGettersAndSetters() {
        User user = new User("12345", "testUser");
        Acc account = new Acc("testAcc", "password123", user);

        assertEquals("testAcc", account.getUsername());
        assertEquals("password123", account.getPassword());
        assertEquals(user, account.getUser());

        account.setUsername("newUsername");
        account.setPassword("newPassword");
        User newUser = new User("67890", "newUser");
        account.setUser(newUser);

        assertEquals("newUsername", account.getUsername());
        assertEquals("newPassword", account.getPassword());
        assertEquals(newUser, account.getUser());
    }
}