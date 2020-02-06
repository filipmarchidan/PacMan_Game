package pacman.database;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

@SuppressWarnings("PMD")
public class UserDao {
    private DbConnect dbConnect;

    public UserDao(DbConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public UserDao() {
        this(new DbConnect());
    }

    /**
     * Method which retrieves user.
     *
     * @param user as param.
     * @return the desired user.
     */
    @SuppressWarnings("PMD")
    public String getUsernameFromDatabase(User user) {

        String finalUsername;
        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT Username FROM Users WHERE Username=?";
        try {
            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setString(1, user.getUsername());
            resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.next()) {
                finalUsername = resultSet.getString("Username");
            } else {
                finalUsername = "No user found";
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
            finalUsername = "Error";
        }
        return finalUsername;
    }

    /**
     * Method which retrieves user.
     *
     * @param user as param.
     * @return the desired user password.
     */
    @SuppressWarnings("PMD")
    public String getUserPasswordFromDatabase(User user) {

        String finalPassword = "";
        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT Password FROM Users WHERE Username=?";
        try {
            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setString(1, user.getUsername());
            resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.next()) {
                finalPassword = resultSet.getString("Password");
            } else {
                finalPassword = "No user password found";
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
            finalPassword = "Error";
        }
        return finalPassword;
    }

    /**
     * Method which retrieves score.
     *
     * @param user as param.
     * @return user's score.
     */
    @SuppressWarnings("PMD")
    public int retrieveScore(User user) {

        int score = 0;
        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT Score FROM Users WHERE Username=?";
        try {
            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setString(1, user.getUsername());
            resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.next()) {
                score = resultSet.getInt("Score");
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
        return score;
    }

    /**
     * Method which retrieves id.
     *
     * @param user as param.
     * @return user's id.
     */
    @SuppressWarnings("PMD")
    public int getUserIdFromDatabase(User user) {

        int id = 0;
        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT Id FROM Users WHERE Username=?";
        try {
            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setString(1, user.getUsername());
            resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("Id");
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
        return id;
    }

    /**
     * Method which updates user score.
     *
     * @param user as param.
     */

    public void updateUserScore(User user) {

        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        String query = "UPDATE Users SET Score=? WHERE Username=?";
        try {
            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setInt(1, user.getScore());
            ((PreparedStatement) statement).setString(2, user.getUsername());
            ((PreparedStatement) statement).executeUpdate();
            /*
            if (((PreparedStatement) statement).executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Successfully updated", "Database info", 1);
            }
             */
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }

    /**
     * Method which updates username.
     *
     * @param user as param.
     */
    @SuppressWarnings("PMD")
    public void updateUserUsername(User user) {
        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        String query = "UPDATE Users SET Username=? WHERE Id=?";
        try {
            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setString(1, user.getUsername());
            ((PreparedStatement) statement).setInt(2, user.getId());
            ((PreparedStatement) statement).executeUpdate();
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }

    /**
     * Method which updates password.
     *
     * @param user as param.
     */
    @SuppressWarnings("PMD")
    public void updateUserPassword(User user) {

        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        EncryptionDao encryptionDao = new EncryptionDao();
        byte[] encryptedPass = new byte[64];
        String userSalt = encryptionDao.getUserSalt(user);

        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();
        try {
            encryptedPass = passwordEncryptionService.getEncryptedPassword(user.getPassword(),
                    Base64.getDecoder().decode(userSalt));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error occurred");
        }
        String query = "UPDATE Users SET Password=? WHERE Username=?";
        try {
            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setString(1,
                    Base64.getEncoder().encodeToString(encryptedPass));
            ((PreparedStatement) statement).setString(2, user.getUsername());
            ((PreparedStatement) statement).executeUpdate();
            /*
            if (((PreparedStatement) statement).executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Successfully updated", "Database info", 1);
            }
             */
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }

    /**
     * Method which deletes user.
     *
     * @param user as param.
     */
    @SuppressWarnings("PMD")
    public void deleteUser(User user) {

        Connection conn = dbConnect.getMyConnection();
        Statement statement;
        String query = "DELETE FROM Users WHERE Username=?";
        try {
            statement = conn.prepareStatement(query);
            ((PreparedStatement) statement).setString(1, user.getUsername());
            ((PreparedStatement) statement).executeUpdate();
            /*
            if (((PreparedStatement) statement).executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Successfully updated", "Database info", 1);
            }
            */
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }

}
