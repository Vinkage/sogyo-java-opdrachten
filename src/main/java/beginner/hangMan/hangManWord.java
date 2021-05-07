package beginner.hangMan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class hangManWord {

    private ArrayList<Character> theWord = new ArrayList<Character>();
    private ArrayList<Character> displayedWord = new ArrayList<Character>();
    private Set<Character> remainingLetters = new HashSet<>();

    public void setUp(String randomWord) {
        // Sets the fields of a newly created hangManWord object given a randomWord
        char empty = '_';
        for (char letter : randomWord.toCharArray()) {
            // A list of letters representing the correct word
            theWord.add(letter);
            // A list of letters that is displayed to the user
            displayedWord.add(empty);
            // A set of unique letters that remain to be guessed
            remainingLetters.add(letter);
        }
    }

    public void display() {
        // Displays the displayedWord field
        for (char letter : displayedWord) {
            System.out.print(letter + " ");
        }
        System.out.println("");
    }

    public Boolean checkGuess(char userInput) {
        // Method that checks
        // if the userInput parameter is a letter that remains to be guessed, then remove that letter from the remaining letters
        //                  and update the displayedWord field, returns true
        // Otherwise return false
        if (remainingLetters.contains(userInput)) {
            remainingLetters.remove(userInput);

            for (int i = 0; i < theWord.size(); i++) {
                char letter = theWord.get(i);
                if (!remainingLetters.contains(letter)) {
                    displayedWord.set(i, letter);
                }
            }

            return true;
        }
        return false;
    }

    public Set<Character> getRemaining() {
        // Method that allows other classes to access the remainingLetters set
        return remainingLetters;
    }

    public void displayCorrectWord() {
        // Method called if the user loses, to show what was the correct word
        for (char letter : theWord) {
            System.out.print(letter);
        }
        System.out.println();
    }

    public boolean alreadyDisplayed(char userInput) {
        // Check if a letter is already displayed
        return displayedWord.contains(userInput);
    }
}
