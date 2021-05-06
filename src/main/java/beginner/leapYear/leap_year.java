package beginner.leapYear;


public class leap_year {


    public Boolean checkLeapYear(int inputYear) {
        //assume false leap year
        Boolean isLeapYear = false;
        // necessary for logic
        Boolean fourFits = divisibleBy(inputYear, 4);
        Boolean hundredFits = divisibleBy(inputYear, 100);
        Boolean fourHundredFits = divisibleBy(inputYear, 400);
        // printable year string
        String displayedYear = Integer.toString(inputYear);

        if (hundredFits) {

            // new century suite
            if (fourHundredFits) {
                System.out.println(displayedYear + " is a new century and a leap year.");
                isLeapYear = true;
            } else {
                System.out.println(displayedYear + " is not a leap year, new century must be divisible by 4 and 100.");
            }

        } else {

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

    public static void main(String args[])  {
        //create objects needed for the main program
        leap_year leapYear = new leap_year();
        leap_year_helper leapYearHelper = new leap_year_helper();
        // flag to check when the main program is done
        Boolean jobDone = false;

        //wanted to try out lambda functions in java for the first time, don't know how to use them in tests nicely
        // without copying them.
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

        while (!jobDone) {
            String userInputYear = leapYearHelper.getUserInput("Please type a valid year and press enter: ", yearCheckLambda);
            if (userInputYear == null) {
                System.out.println("Sorry, your input can only contain numbers.");
                continue;
            }

            int inputYear = Integer.parseInt(userInputYear);

            leapYear.checkLeapYear(inputYear);
            Boolean tryAgain = false;
            Boolean validAnswer = false;
            while (!validAnswer) {
                String userYesNo = leapYearHelper.getUserInput("Do you want to try again? yes (y) or no (n)", yesNoLambda);
                if (userYesNo == null) {
                    System.out.println("Sorry, I didn't understand you.");
                    continue;
                }

                if (userYesNo.equals("y")) {
                    jobDone = false;
                } else {
                    jobDone = true;
                    leapYear.jobDoneMessage();
                }
                validAnswer = true;
            }
        }
    }

}
