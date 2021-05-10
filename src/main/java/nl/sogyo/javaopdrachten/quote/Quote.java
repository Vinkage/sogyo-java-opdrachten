package nl.sogyo.javaopdrachten.quote;

import java.util.Calendar;
import java.util.Date;

public class Quote {
    String[][] quotes = {
        {"galileo", "eppur si muove"},
        {"archimedes", "eureka!"},
        {"erasmus", "in regione caecorum rex est luscus"},
        {"socrates", "I know nothing except the fact of my ignorance"},
        {"rené descartes", "cogito, ergo sum"},
        {"sir isaac newton", "if I have seen further it is by standing on the shoulders of giants"}
    };

    public static void main(String... args) {
        Date dateObj = new Date();
        Calendar calObj = Calendar.getInstance();

        Quote thisQuote = new Quote();
        QuoteString thisQuoteString = new QuoteString();
        QuoteFormatting thisFormatter = new QuoteFormatting();

        // Gets day of year
        calObj.setTime(dateObj);
        int dayOfYear = calObj.get(Calendar.DAY_OF_YEAR);
        // Gets the quote index
        int quoteIndex = (dayOfYear % thisQuote.quotes.length);
        // Sets a formatted date string
        thisQuoteString.setDate(thisFormatter.formatDate(calObj));

        // Sets a capitalized sentence
        String[] quoteArray = thisQuote.quotes[quoteIndex];
        thisQuoteString.setSentence(thisFormatter.capitalizeString(quoteArray[1]));

        String[] quoteName = quoteArray[0].split(" ");
        String nameFormatted = "";
        for (String name : quoteName) {
            nameFormatted = nameFormatted + " " + thisFormatter.capitalizeString(name);
        }
        thisQuoteString.setNameString(nameFormatted.strip());

        if (thisQuoteString.getSentenceString().matches(".*[.,!?]$")) {
            System.out.println("Quote for " + thisQuoteString.getDateString() + ":");
            System.out.println("\"" + thisQuoteString.getSentenceString() + "\" -- " + thisQuoteString.getNameString());
        } else {
            System.out.println("Quote for " + thisQuoteString.getDateString() + ":");
            System.out.println("\"" + thisQuoteString.getSentenceString() + ".\" -- " + thisQuoteString.getNameString());
        }

    }
}
