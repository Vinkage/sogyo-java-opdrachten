package beginner.leapYear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class leap_year_helper {

    public String getUserInput(String prompt) {
        String inputLine = null;
        System.out.print(prompt + ' ');
        try {
            BufferedReader is = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            inputLine = is.readLine();

            if (!checkInput(inputLine)) {
                return null;
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine;
        }

    public Boolean checkInput(String year) {
        boolean isInt = year.matches("[0-9]+");
        if (isInt)
            return true;
        return false;
    }
}
