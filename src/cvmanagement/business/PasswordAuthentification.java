package cvmanagement.business;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cvmanagement.business.interfaces.IPasswordAuthentification;

/**
 * Hash passwords for storage, and test passwords against password tokens.
 * 
 * Instances of this class can be used concurrently by multiple threads.
 * 
 * @author erickson
 * @see <a href="http://stackoverflow.com/a/2861125/3474">StackOverflow</a>
 */
public class PasswordAuthentification  implements IPasswordAuthentification, Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -1942768262417817068L;

    @Override
    public String hash(String passwordToHash) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Add password bytes to digest
            md.update(passwordToHash.getBytes());
            // Get the hash's bytes
            byte[] bytes = md.digest();
            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // yGet complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    @Override
    public boolean authenticate(String password, String token) {
        return token.equals(hash(password));
    }
}