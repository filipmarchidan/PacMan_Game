package pacman.database;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class LeaderboardDaoTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private LeaderboardDao dao;
    private PreparedStatement statement;

    /**
     * Set up the environment for the db context under testing.
     *
     * @throws SQLException in case prepared statements are wrong.
     */
    @BeforeEach
    public void setup() throws SQLException { // NOPMD spelled correctly
        ResultSet result = mock(ResultSet.class); // NOPMD mocks do not need to be closed
        when(result.next()).thenReturn(true).thenReturn(false);
        when(result.getInt("Id")).thenReturn(0);
        when(result.getString("Username")).thenReturn("TestUser");
        when(result.getString("Password")).thenReturn("TestPass");
        when(result.getInt("Score")).thenReturn(420);
        statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(result);
        Connection conn = mock(Connection.class);  // NOPMD mocks do not need to be closed
        when(conn.prepareStatement(anyString())).thenReturn(statement);
        DbConnect dbConn = mock(DbConnect.class);
        when(dbConn.getMyConnection()).thenReturn(conn);
        dao = new LeaderboardDao(dbConn);
    }

    @Test
    public void testGetTop() throws SQLException {
        List<User> top = dao.getTop(5);
        verify(statement).setInt(1, 5);
        assertEquals(1, top.size());
        assertEquals("TestUser", top.get(0).getUsername());
    }

    @Test
    public void testEnterScore() throws SQLException {
        User user = new User();
        user.setUsername("Example1");
        user.setPassword("pass1");
        user.setScore(20);
        LeaderboardDao leaderboardDao = new LeaderboardDao();
        RegisterDao registerDao = new RegisterDao();
        registerDao.addUser(user);
        leaderboardDao.enterScore(user, 300);
        UserDao userDao = new UserDao();
        int userScore = userDao.retrieveScore(user);
        userDao.deleteUser(user);
        assertEquals(300, userScore);
    }

}
