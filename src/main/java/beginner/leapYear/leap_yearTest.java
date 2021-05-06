package beginner.leapYear;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class leap_yearTest {

    private leap_year testObj;

    @BeforeEach
    void setUp() {
        testObj = new leap_year();
    }

    @Test
    void divisibleByTest() {
        //divisible thing
        int testFactor = 4;
        int testInputYear = 4;
        Boolean testResult = testObj.divisibleBy(testInputYear, testFactor);
        assertTrue(testResult);

        // not divisible thing
        int testFactor2 = 4;
        int testInputYear2 = 5;
        Boolean testResult2 = testObj.divisibleBy(testInputYear2, testFactor2);
        assertFalse(testResult2);
    }

    @Test
    void checkLeapYearTest() {
        // User input is always integer

        // divisible by 4
        int testUserInput = 4;
        Boolean isLeapYear = testObj.checkLeapYear(testUserInput);
        assertTrue(isLeapYear);

        // not divisible by 4
        int testUserInput2 = 5;
        Boolean isLeapYear2 = testObj.checkLeapYear(testUserInput2);
        assertFalse(isLeapYear2);

        // new century divisible by 4 and 100
        int testUserInput3 = 4000;
        Boolean isLeapYear3 = testObj.checkLeapYear(testUserInput3);
        assertTrue(isLeapYear3);

        // new century divisible by 4 and not 100
        int testUserInput4 = 4100;
        Boolean isLeapYear4 = testObj.checkLeapYear(testUserInput4);
        assertFalse(isLeapYear4);
    }

    @AfterEach
    void tearDown() {
    }
}