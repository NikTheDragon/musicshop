package by.kurlovich.musicshop.util;

import by.kurlovich.musicshop.entity.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserUtilTest {
    User user;

    @BeforeClass
    public void setUp() {
        user = new User();
        user.setId("1");
    }

    @AfterClass
    public void tearDown() {
        user = null;
    }

    @Test
    public void positiveGetIdTest(){
        String actual = UserUtil.getId(user);
        String expected = "1";

        Assert.assertEquals(actual, expected, "id's mismatch.");
    }

    @Test
    public void nullGetIdTest(){
        String actual = UserUtil.getId(null);
        String expected = "0";

        Assert.assertEquals(actual, expected, "id's mismatch.");
    }

    @Test
    public void negativeGetIdTest(){
        String actual = UserUtil.getId(user);
        String expected = "0";

        Assert.assertNotEquals(actual, expected, "the same id's.");
    }
}
