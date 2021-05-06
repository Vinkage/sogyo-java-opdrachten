package beginner.leapYear;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class leap_year_helperTest {

    private leap_year_helper Helper;

    @BeforeEach
    public void setUp() throws Exception {
        Helper = new leap_year_helper();
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
    @DisplayName("input checker test")
    public void testSimulateUser() {
        // taken from https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

    }
}