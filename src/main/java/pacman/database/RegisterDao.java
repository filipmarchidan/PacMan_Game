package pacman.database;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

@SuppressWarnings("PMD")
public class RegisterDao {
    private DbConnect dbConnect;

    public RegisterDao(DbConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public RegisterDao() {
        this(new DbConnect());
    }

    /**
     * Method that checks if user exists.
     *
     * @param user as param.
     */

    public boolean checkUserAlreadyExists(User user) {
        Connection conn = dbConnect.getMyConnection();
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "SELECT Username FROM Users WHERE Username=?";
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, user.getUsername());

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false;
            }
            statement.close();
            resultSet.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
        return true;
    }

    /**
     * Method that adds a new user.
     *
     * @param user as param.
     */
    @SuppressWarnings("PMD")
    public void addUser(User user) {
        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();
        byte[] encryptedPass = new byte[64];
        byte[] userSalt = new byte[8];
        try {
            userSalt = passwordEncryptionService.getSalt();
            System.out.println("Salt add " + userSalt);
            System.out.println("Salt string " + Base64.getEncoder().encodeToString(userSalt));
            encryptedPass = passwordEncryptionService.getEncryptedPassword(user.getPassword(),
                    userSalt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("error occurred");
        }
        executeQuery(user, encryptedPass, userSalt);
    }

    /**
     * executes the sql query inside the db.
     *
     * @param user          with this new user
     * @param encryptedPass with thi encrypted password to store
     * @param userSalt      and this user's salt.
     */
    public void executeQuery(User user, byte[] encryptedPass, byte[] userSalt) {
        Connection conn = dbConnect.getMyConnection(); //NOPMD connection is closed
        String query = "INSERT INTO Users(Username,Password,PassSalt,Score)" + " VALUES(?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, Base64.getEncoder().encodeToString(encryptedPass));
            statement.setString(3, Base64.getEncoder().encodeToString(userSalt));
            statement.setInt(4, user.getScore());
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error occurred in adding user");
        }
    }

}
