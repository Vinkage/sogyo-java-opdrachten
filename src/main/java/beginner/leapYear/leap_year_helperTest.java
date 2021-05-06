package beginner.leapYear;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class leap_year_helperTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    private leap_year_helper Helper;
    private leap_year_helper.MyInputChecker yearCheckLambda = (String year) -> {
        boolean isInt = year.matches("[0-9]+");
        if (isInt)
            return true;
        return false;
    };

    private leap_year_helper.MyInputChecker yesNoLambda = (String input) -> {
        boolean isYesNo = input.matches("(y)|(n)");
        if (isYesNo)
            return true;
        return false;
    };

    @BeforeEach
    public void setUp() throws Exception {
        Helper = new leap_year_helper();
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    @DisplayName("input checker test")
    public void testYearInput() {
        String yearInput = "1994";
        String nullInput = "";
        String characterInput = "wioefwe";
        assertAll(
                () -> assertTrue(Helper.checkInput(yearInput), "Check input is not true when it should be"),
                () -> assertFalse(Helper.checkInput(nullInput), "Check input is not true when it should be"),
                () -> assertFalse(Helper.checkInput(characterInput), "Check input is not true when it should be")
        );
    }

    @Test
    @DisplayName("Simulated input test")
    public void testSimulateUser() {
        final String testString = "hello!";
        provideInput(testString);
        String Result = Helper.getUserInput("Enter your input: ", yearCheckLambda);
        // Null because the input is not a year
        assertNull(Result);

        final String testString2 = "1994";
        provideInput(testString2);
        String Result2 = Helper.getUserInput("Enter your input: ", yearCheckLambda);
        // If input consists only of integers than that input string is returned
        assertEquals("1994", Result2);
    }
}