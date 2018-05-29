package by.kurlovich.musicshop.util;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

public class PasswordSHAGeneratorTest {

    @BeforeClass
    public void setUp() {
    }

    @AfterClass
    public void tearDown() {
    }

    @Test
    public void positiveGeneratorTest() throws NoSuchAlgorithmException {
        String password = "root";

        String expected = "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2";
        String actual = PasswordSHAGenerator.getPassword(password);

        Assert.assertEquals(expected, actual, "SHA mismatch.");
    }

    @Test
    public void negativeGeneratorTest() throws NoSuchAlgorithmException {
        String password = "root2";

        String expected = "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2";
        String actual = PasswordSHAGenerator.getPassword(password);

        Assert.assertNotEquals(expected, actual, "SHA generates similar hashes for different passwords.");
    }
}
