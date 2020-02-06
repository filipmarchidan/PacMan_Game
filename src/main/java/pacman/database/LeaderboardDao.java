package pacman.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class LeaderboardDao {

    private DbConnect connect;

    public LeaderboardDao() {
        this(new DbConnect());
    }

    public LeaderboardDao(DbConnect connect) {
        this.connect = connect;
    }

    /**
     * Updates the score of a user in the database.
     *
     * @param user  The user to update
     * @param score The user's new score
     */
    public void enterScore(User user, int score) {
        try (Connection conn = connect.getMyConnection()) {
            PreparedStatement statement = conn
                    .prepareStatement("UPDATE Users SET Score=? WHERE Username=?");
            statement.setInt(1, score);
            statement.setString(2, user.getUsername());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the top scores in the database.
     *
     * @param amount The amount of scores to get
     * @return The top users, ordered by descending score
     */
    public @NotNull List<User> getTop(int amount) {
        try (Connection conn = connect.getMyConnection()) {
            PreparedStatement statement = conn
                    .prepareStatement("SELECT * FROM Users ORDER BY Score DESC LIMIT ?");
            statement.setInt(1, amount);

            List<User> result = new ArrayList<>();
            ResultSet results = statement.executeQuery(); // NOPMD Everything is closed
            while (results.next()) {
                User user = new User();
                user.setId(results.getInt("Id"));
                user.setUsername(results.getString("Username"));
                user.setPassword(results.getString("Password"));
                user.setScore(results.getInt("Score"));
                result.add(user);
            }
            statement.close();
            results.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
