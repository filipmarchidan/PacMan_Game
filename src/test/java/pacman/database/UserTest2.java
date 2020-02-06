package pacman.database;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class UserTest2 {

    private User user1;
    private User user2;

    /**
     * Sets up the user for this current mocked session.
     */
    @BeforeEach
    public void setup() { // NOPMD spelled correctly
        user1 = new User();
        user1.setId(1);
        user1.setUsername("User1");
        user1.setPassword("Pass1");
        user1.setScore(10);
        user2 = new User();
        user2.setId(2);
        user2.setUsername("User2");
        user2.setPassword("Pass2");
        user2.setScore(20);
    }

    @Test
    public void testEqualsSame() {
        assertEquals(user1, user1);
    }

    @Test
    public void testEqualsDiff() {
        User user = new User();
        user.setId(1);
        user.setUsername("User1");
        user.setPassword("Pass1");
        user.setScore(10);
        assertEquals(user1, user);
    }

    @Test
    public void testNotEquals() {
        Assert.assertNotEquals(user1, user2);
    }

}
