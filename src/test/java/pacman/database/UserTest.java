package pacman.database;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@SuppressWarnings("PMD")
public class UserTest {
    private User user;
    private User user1;

    /**
     * setting up the testing db environment.
     */
    @BeforeEach
    public void setUp() {
        user = new User();
        user1 = new User();
        user1.setId(23);
        user1.setScore(100);
        user1.setUsername("88Glam");
        user1.setPassword("12345");

        user.setId(19);
        user.setScore(50);
        user.setUsername("Kodak Black");
        user.setPassword("12345");
    }

    @Test
    public void testGetId() {
        assertEquals(23, user1.getId());
    }

    @Test
    public void testSetId() {
        user.setId(25);
        assertEquals(25, user.getId());
    }

    @Test
    public void testGetUsername() {
        assertEquals("88Glam", user1.getUsername());
    }

    @Test
    public void testSetUsername() {
        user1.setUsername("Arizona Zervas");
        assertEquals("Arizona Zervas", user1.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("12345", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("123");
        assertEquals("123", user.getPassword());
    }

    @Test
    public void testGetScore() {
        assertEquals(100, user1.getScore());
    }

    @Test
    public void testSetScore() {
        user1.setScore(150);
        assertEquals(150, user1.getScore());
    }

    @Test
    public void testEqualsFalse() {
        assertEquals(false, user.equals(user1));
    }

    @Test
    public void testEqualsTrue() {
        User user2 = new User();
        user2 = user;
        assertEquals(true, user.equals(user2));
    }
}
