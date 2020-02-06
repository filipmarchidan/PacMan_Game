package pacman.database;

import com.mysql.cj.log.Log;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;


/**
 * New login class.
 */
@SuppressWarnings("PMD")
public class LoginDao {
    /**
     * Method to handle login.
     *
     * @param user which is used for login.
     * @return response if true or false.
     */
    private DbConnect dbConnect;

    public LoginDao(DbConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public LoginDao() {
        this(new DbConnect());
    }

    /**
     * Method to attempt login.
     *
     * @param user user
     * @return boolean if successful.
     */
    public boolean attemptLogin(User user) {
        Connection conn = dbConnect.getMyConnection();
        EncryptionDao encryptionDao = new EncryptionDao();
        byte[] encryptedPass = new byte[64];
        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();
        try {
            String newSalt = encryptionDao.getUserSalt(user);
            if (newSalt.isEmpty()) {
                return false;
            }
            encryptedPass = passwordEncryptionService.getEncryptedPassword(user.getPassword(),
                    Base64.getDecoder().decode(newSalt));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("error occurred");
        }
        boolean status = executeQuery(user, conn, encryptedPass);
        //this can be deleted later if a specific window is implemented
        //make use of either the string or the option pane
        System.out.println(status);
        return status;
    }

    /**
     * Prepares the sql statement and then executes the query
     * for retrieving username and password.
     */
    public boolean executeQuery(User user, Connection conn, byte[] encryptedPass) {
        boolean status = false; //NOPMD
        try {
            String query = "SELECT Username,Password FROM Users WHERE Username=? AND Password=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, Base64.getEncoder().encodeToString(encryptedPass));
            ResultSet resultSet = statement.executeQuery(); //NOPMD
            status = resultSet.next();
            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in logging in");
        }
        return status;
    }

}
