package pacman.database;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("PMD")
public class LoginDaoTest {
    private UserDao userDao;
    private LoginDao loginDao;
    private RegisterDao registerDao;
    private User user;

    /**
     * setting up the testing db environment.
     */
    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("Lil Boat");
        user.setPassword("12345");
        user.setScore(10);
        registerDao = new RegisterDao();
        registerDao.addUser(user);
    }

    @Test
    public void mockitoLogin() {
        loginDao = Mockito.mock(LoginDao.class);
        Mockito.when(loginDao.attemptLogin(user)).thenReturn(true);
    }

    @Test
    public void testGoodAttempt() {
        loginDao = new LoginDao();
        assertEquals(true, loginDao.attemptLogin(user));
    }

    @Test
    public void testFailureAttempt() {
        User user1 = new User();
        user1.setUsername("Leo");
        user1.setPassword("puiu");
        user1.setScore(10);
        loginDao = new LoginDao();
        assertEquals(false, loginDao.attemptLogin(user1));
    }


    @AfterEach
    public void end() {
        UserDao userDao = new UserDao();
        userDao.deleteUser(user);
    }
}
