package pacman.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class EncryptionDao {
    /**
     * Method which retrieves the salt for the user.
     *
     * @param user as param.
     * @return user's salt.
     */
    @SuppressWarnings("PMD")
    public String getUserSalt(User user) {
        DbConnect dbConnect = new DbConnect();
        String salt = "";
        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT PassSalt FROM Users WHERE Username=?";
        try {

            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setString(1, user.getUsername());
            resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.next()) {
                salt = (resultSet.getString("PassSalt"));
                System.out.println("Salt is" + salt);
            }
            resultSet.close();
            statement.close();
            conn.close();
            return salt;
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
        return salt;
    }
}
