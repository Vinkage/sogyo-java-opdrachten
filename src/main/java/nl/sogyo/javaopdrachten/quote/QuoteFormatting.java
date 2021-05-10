package nl.sogyo.javaopdrachten.quote;

import java.util.Calendar;
import java.util.Locale;

public class QuoteFormatting {

    public String formatDate(Calendar calObj) {
        // https://stackoverflow.com/questions/14832151/how-to-get-month-name-from-calendar
        String dayOfWeek = calObj.getDisplayName(calObj.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        String month = calObj.getDisplayName(calObj.MONTH, Calendar.LONG, Locale.getDefault());
        String dayOfMonth = Integer.toString(calObj.get(Calendar.DAY_OF_MONTH));
        switch (dayOfMonth) {
            case "1":
                dayOfMonth = dayOfMonth + "th";
                break;
            case "2":
                dayOfMonth = dayOfMonth + "nd";
            case "3":
                dayOfMonth = dayOfMonth + "rd";
            default:
                dayOfMonth = dayOfMonth + "th";
        }
        String formattedDateString = dayOfWeek + " the " + dayOfMonth + " of " + month;
        return formattedDateString;
    }

    public String capitalizeString(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }
}
