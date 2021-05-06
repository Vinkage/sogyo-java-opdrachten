package beginner.leapYear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class leap_year_helper {

    public interface MyInputChecker {
        public boolean check(String Input);
    }

    public String getUserInput(String prompt, MyInputChecker checker) {
        String inputLine = null;
        System.out.print(prompt + ' ');
        try {
            BufferedReader is = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            inputLine = is.readLine();

            if (!checker.check(inputLine)) {
                return null;
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine;
        }
}
