package beginner.hangMan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// largely taken from leapYearHelper
public class hangManInputHelper {

    public interface inputCheckLambda {
        public boolean check(String Input);
    }

    public String getUserInput(String prompt, inputCheckLambda checker) {
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
