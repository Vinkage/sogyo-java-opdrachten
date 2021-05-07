package beginner.hangMan;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class hangMan {
    private Boolean finished = false;

    private int GuessCount;
    private int MaxGuesses = 10;
    private Set<Character> missedLetters = new HashSet<>();

    private List<String> wordList;
    private String randomWord;
    private hangManInputHelper InputHelper = new hangManInputHelper();

    public static void main(String[] args) {
        // Instantiate objects keeping track of game state and word state
        hangMan Game = new hangMan();
        hangManWord Word = new hangManWord();

        // Set wordList and randomWord fields of Game
        Game.setWordList();
        Game.setrandomWord();

        // Sets up the Word object that keeps track of the word state
        Word.setUp(Game.randomWord);
        Word.display();

        while (!Game.finished) {

            char userInput = Game.getInput();

            // if already displayed print message and don't count as guess
            if (Word.alreadyDisplayed(userInput)) {
                Game.alreadyDisplayedMessage();
                Word.display();
                continue;
            }

            Boolean correctGuess = Word.checkGuess(userInput);
            System.out.println();
            Word.display();

            // main logic for guesses,
            // if already in missed letters print message and don't count as guess
            // if incorrect guess and not the other options, then count as "wrong" guess
            if (Game.missedLetters.contains(userInput)) {
                Game.alreadyInMissedMessage();
            }  else if (!correctGuess) {
                Game.incrementGuesses();
                Game.missedLetters.add(userInput);
                Game.printGuessesLeftMessage();
            }

            // main logic for game ending
            // If max guesses has been reached, go to end of main loop to play again maybe
            // else if the set of remaining letters to be guessed is empty, then the player has won and go to play again
            // otherwise go to beginning outer loop
            if (Game.GuessCount >= Game.MaxGuesses) {
                Game.printLoseMessage();
                Word.displayCorrectWord();
            } else if (Word.getRemaining().isEmpty()) {
                Game.printWinMessage();
            } else {
                continue;
            }

            // Play again logic
            // if not play again then end the outer loop
            // else, reset guess count and missed letters, and choose new random word to start the game again
            if (!Game.playAgain()) {
                Game.finished = true;
            } else {
                Game.setrandomWord();
                Game.GuessCount = 0;
                Game.missedLetters = new HashSet<>();
                Word = new hangManWord();
                Word.setUp(Game.randomWord);
                Word.display();
            }

        }

    }

    private void alreadyDisplayedMessage() {
        System.out.println("The character was already correctly chosen.");
        System.out.println("");
    }

    private void printWinMessage() {
        System.out.println("Nice job you guessed the word!!! : )");
        System.out.println("");
    }

    private void printLoseMessage() {
        System.out.println("Sorry, you have guessed 10 times. The correct word was: ");
    }

    private Boolean playAgain() {
        Boolean validAnswer = false;
        Boolean userChoice = false;
        while (!validAnswer) {

            String userInput = InputHelper.getUserInput(
                    "Do you want to play again? yes (y) or no (n)",
                    (String InputString) -> {return InputString.matches("(y)|(n)");}
            );

            if (userInput == null) {
                System.out.println("Sorry, I didn't understand you.");
                continue;
            } else if (userInput.equals("y")) {
                userChoice = true;
            }
            validAnswer = true;
        }
        return userChoice;
    }

    private void printGuessesLeftMessage() {
        System.out.print((MaxGuesses - GuessCount) + " guesses left. Missed letters: ");
        printMissedLetters();
        System.out.println();
    }

    private void alreadyInMissedMessage() {
        System.out.print((MaxGuesses - GuessCount) + " guesses left. Guess was already in missed letters: ");
        printMissedLetters();
        System.out.println();
    }

    private void printMissedLetters() {
        for (char letter : missedLetters) {
            System.out.print(letter + ", ");
        }
    }

    private void setWordList() {
        // Sets the word list to a list of words read from a txt file
        URL url = getClass().getResource("hangman_words.txt");
        Path wordsFile = Paths.get(url.getPath());
        try {
            wordList = Files.readAllLines(wordsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setrandomWord() {
        // chooses a random word from the word list
        int randomIndex = (int) (Math.random() * wordList.size());
        randomWord = wordList.get(randomIndex);
    }

    private void incrementGuesses() {
        // Increments the guesses field by one
        GuessCount++;
    }

    private char getInput() {
        // Uses the hangManHelper to get valid input from the user
        Boolean validInput = false;
        char userInputChar = 0;
        while (!validInput) {
            String userInput = InputHelper.getUserInput("input letter: ", (inputString) -> {return inputString.matches("[a-zA-Z]");});
            if (userInput != null) {
                userInputChar = userInput.charAt(0);
                userInputChar = Character.toLowerCase(userInputChar);
                validInput = true;
                continue;
            }
            System.out.println("Please input a letter.");
        }
        return userInputChar;
    }

}
