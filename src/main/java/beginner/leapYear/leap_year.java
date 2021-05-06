package beginner.leapYear;


public class leap_year {

    public Boolean checkLeapYear(int inputYear) {
        //assume false leap year
        Boolean isLeapYear = false;
        // necessary for logic flow
        Boolean fourFits = divisibleBy(inputYear, 4);
        Boolean hundredFits = divisibleBy(inputYear, 100);
        Boolean fourHundredFits = divisibleBy(inputYear, 400);
        // printable year string
        String displayedYear = Integer.toString(inputYear);

        // check if new century
        if (hundredFits) {

            // new century suite
            if (fourHundredFits) {
                System.out.println(displayedYear + " is a new century and a leap year.");
                isLeapYear = true;
            } else {
                System.out.println(displayedYear + " is not a leap year, new century must be divisible by 4 and 100.");
            }

        } else {// not new century suite

            if (fourFits) {
                System.out.println(displayedYear + " is a leap year.");
                isLeapYear = true;
            } else {
                System.out.println(displayedYear + " is not a leap year, it is not divisible by four.");
            }

        }
        return isLeapYear;
    }

    public void jobDoneMessage() {
        System.out.println("Goodbye and see you next year.");
    }

    public Boolean divisibleBy(int inputYear, int factor) {
        if (inputYear % factor == 0)
            return true;
        return false;
    }

    public static void main(String args[]) {
        //create objects needed for the main program
        // main object
        leap_year leapYear = new leap_year();
        // helper object for getting user input and checking input with lambda functions
        leap_year_helper leapYearHelper = new leap_year_helper();
        // flag to check when the main program is done
        Boolean jobDone = false;

        //wanted to try out lambda functions in java for the first time, don't know how to use them in tests nicely
        // without copying them.
        // or where to define them.
        // or if it is overkill for this program...
        leap_year_helper.MyInputChecker yearCheckLambda = (String year) -> {
            boolean isInt = year.matches("[0-9]+");
            if (isInt)
                return true;
            return false;
        };

        leap_year_helper.MyInputChecker yesNoLambda = (String input) -> {
            boolean isYesNo = input.matches("(y)|(n)");
            if (isYesNo)
                return true;
            return false;
        };

        // main/ outer loop
        //REPEAT until job is done
        while (!jobDone) {
            // GET user input
            String userInputYear = leapYearHelper.getUserInput("Please type a valid year and press enter: ", yearCheckLambda);
            // IF null then try again
            if (userInputYear == null) {
                System.out.println("Sorry, your input can only contain numbers.");
                continue;
            }

            // CONVERT input to int
            int inputYear = Integer.parseInt(userInputYear);

            // EVALUATE and DISPLAY input and output result using own method
            leapYear.checkLeapYear(inputYear);


            // REPEAT until we have a valid input confirming the user wants to try again
            Boolean validAnswer = false;
            while (!validAnswer) {
                // GET user input to try to test another year
                String userYesNo = leapYearHelper.getUserInput("Do you want to try again? yes (y) or no (n)", yesNoLambda);
                // IF input is null then ask again if the user wants to try again
                if (userYesNo == null) {
                    System.out.println("Sorry, I didn't understand you.");
                    continue;
                }

                // IF input is "y" then go to beginning of main/outer loop
                if (userYesNo.equals("y")) {
                    jobDone = false;
                } else {// ELSE end job and DISPLAY a ending message
                    jobDone = true;
                    leapYear.jobDoneMessage();
                }
                validAnswer = true;
            }
        }
    }

}
