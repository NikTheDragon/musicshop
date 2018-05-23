package by.kurlovich.musicshop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSHAGenerator {
    public static String generate(String password) throws NoSuchAlgorithmException {
        byte[] mySalt = "mySaltArray".getBytes();

        return getSecurePassword(password, mySalt);
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt) throws NoSuchAlgorithmException {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);

            byte[] bytes = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();

            return generatedPassword;

        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
    }
}
