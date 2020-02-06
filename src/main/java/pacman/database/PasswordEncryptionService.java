package pacman.database;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Encryption class.
 */
public class PasswordEncryptionService {

    /**
     * method which gets an encrypted pass.
     *
     * @param password pass.
     * @param salt     salt.
     * @return an encrypted pass.
     * @throws NoSuchAlgorithmException if there is none.
     * @throws InvalidKeySpecException  if there is invalid.
     */
    public byte[] getEncryptedPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String encryptionType = "PBKDF2WithHmacSHA1";
        int keyLength = 160;
        int iterations = 20000;
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(encryptionType);
        return secretKeyFactory.generateSecret(keySpec).getEncoded();
    }

    /**
     * method which gets a salt.
     *
     * @return new salt.
     * @throws NoSuchAlgorithmException if there is none.
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * method which checks security.
     *
     * @param password          pass.
     * @param encryptedPassword crypto pass.
     * @param salt              salt.
     * @return false or true.
     * @throws NoSuchAlgorithmException if there is none.
     * @throws InvalidKeySpecException  if there is invalid.
     */
    public boolean securityCheck(String password, byte[] encryptedPassword, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] attempt = getEncryptedPassword(password, salt);
        return Base64.getEncoder().encodeToString(attempt)
                .equals(Base64.getEncoder().encodeToString(encryptedPassword));
    }
}
