package by.kurlovich.musicshop.util.validator;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class AccessValidatorTest {
    List<String> accessRoles;

    @BeforeClass
    public void setUp() {
        accessRoles = Arrays.asList("admin", "user");
    }

    @AfterClass
    public void tearDown() {
        accessRoles = null;
    }

    @Test
    public void positiveValidatorTest() {
        String role = "admin";

        boolean actual = AccessValidator.validate(accessRoles, role);
        boolean expected = true;

        Assert.assertEquals(actual, expected, "can't validate role.");
    }

    @Test
    public void negativeValidatorTest() {
        String role = "guest";

        boolean actual = AccessValidator.validate(accessRoles, role);
        boolean expected = true;

        Assert.assertNotEquals(actual, expected, "validate bad role.");
    }

    @Test
    public void nullValidatorTest() {
        String role = "guest";

        boolean actual = AccessValidator.validate(accessRoles, null);
        boolean expected = true;

        Assert.assertNotEquals(actual, expected, "validate bad role.");
    }
}

