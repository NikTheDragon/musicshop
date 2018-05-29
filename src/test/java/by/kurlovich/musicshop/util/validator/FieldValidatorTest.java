package by.kurlovich.musicshop.util.validator;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FieldValidatorTest {
    String[] text;

    @BeforeClass
    public void setUp() {

    }

    @AfterClass
    public void tearDown() {

    }

    @Test
    public void normalTextFieldTest() {
        text = new String[]{"message"};

        String actual = FieldValidator.validateTextField(text);
        String expected = "true";

        Assert.assertEquals(actual, expected, "incorrect text validation.");
    }

    @Test
    public void nullTextFieldTest() {
        String actual = FieldValidator.validateTextField(null);
        String expected = "null";

        Assert.assertEquals(actual, expected, "incorrect text validation.");
    }

    @Test
    public void symbolTextFieldTest() {
        text = new String[]{"message$"};

        String actual = FieldValidator.validateTextField(text);
        String expected = "notText";

        Assert.assertEquals(actual, expected, "incorrect text validation.");
    }

    @Test
    public void lengthTextFieldTest() {
        text = new String[]{"m"};

        String actual = FieldValidator.validateTextField(text);
        String expected = "length";

        Assert.assertEquals(actual, expected, "incorrect text validation.");
    }

}
