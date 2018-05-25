package by.kurlovich.musicshop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSHAGenerator {
    public static String getPassword(String password) throws NoSuchAlgorithmException {

        return getSecurePassword(password);
    }

    private static String getSecurePassword(String passwordToHash) throws NoSuchAlgorithmException {
        try {
            String generatedPassword;
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] byteArray = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();
            for (Byte bytes : byteArray) {
                sb.append(Integer.toString((bytes & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();

            return generatedPassword;

        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
    }
}
