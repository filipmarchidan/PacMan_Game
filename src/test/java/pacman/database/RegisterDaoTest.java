package pacman.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("PMD")
class RegisterDaoTest {

    private UserDao userDao;
    private RegisterDao registerDao;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(0);
        user.setUsername("SpongeBob");
        user.setPassword("12345");
        user.setScore(10);
        registerDao = new RegisterDao();
    }

    @Test
    void checkUserAlreadyExists() {
        user = new User();
        userDao = new UserDao();
        user.setUsername("Lil Peep");
        user.setPassword("12345");
        user.setScore(10);
        RegisterDao registerDao = new RegisterDao();
        registerDao.addUser(user);
        assertTrue(registerDao.checkUserAlreadyExists(user));
    }

    @Test
    void addUser() {
        User user2 = new User();
        userDao = new UserDao();
        user2.setUsername("Lil Peep");
        user2.setPassword("12345");
        user2.setScore(10);
        User userCopy = user2;
        RegisterDao registerDao = new RegisterDao();
        registerDao.addUser(user2);
        assertEquals(userCopy.getUsername(), userDao.getUsernameFromDatabase(user2));
        userDao.deleteUser(user2);
    }

    @Test
    void checkFalseUser() {
        user = new User();
        userDao = new UserDao();
        user.setUsername("Lil Peep");
        user.setPassword("12345");
        user.setScore(10);
        assertEquals(false, registerDao.checkUserAlreadyExists(user));
    }

    @Test
    void testDuplicates() {
        User user2 = user;
        RegisterDao registerDao = Mockito.mock(RegisterDao.class);
        registerDao.addUser(user);
        Mockito.when(registerDao.checkUserAlreadyExists(user2)).thenReturn(true);
    }

    @AfterEach
    void end() {
        userDao = new UserDao();
        userDao.deleteUser(user);
    }
}