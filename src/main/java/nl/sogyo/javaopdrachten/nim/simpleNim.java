package nl.sogyo.javaopdrachten.nim;

import java.util.*;

public class simpleNim {
    private ArrayList<Position> positions = new ArrayList();
    private Position gamePosition;
    private int matches;
    private HashSet<Integer> allowedToTakeSet;
    private String currentPlayer;

    public simpleNim(int matches, int allowedToTakeUpto) {
        this.matches = matches;
        this.allowedToTakeSet = new HashSet<>();
        for (int i = 1; i <= allowedToTakeUpto; i++) {
            this.allowedToTakeSet.add(i);
        }
    }

    public void setUpPositions() {
        positions.add(new LosingPosition(1));
        for (int i = 2; i <= this.matches; i++) {

            ArrayList<Position> lowerPositions = getLowerPositions(i);
            if (containsLosingPosition(lowerPositions)) {
                positions.add(new WinningPosition(i));
            } else {
                positions.add(new LosingPosition(i));
            }

            setMovePositions(positions.get(i-1), lowerPositions);
        }
    }

    private void setMovePositions(Position currentPosition, ArrayList<Position> lowerPositions) {
        for (Position positionToPotentiallyAdd : lowerPositions) {
            currentPosition.addReachablePosition(positionToPotentiallyAdd);
        }
    }

    private Boolean containsLosingPosition(ArrayList<Position> lowerPositions) {
        for (Position position : lowerPositions) {
            if (position instanceof LosingPosition) return true;
        }
        return false;
    }

    private ArrayList<Position> getLowerPositions(int positionInt) {
        ArrayList<Position> lowerPositions = new ArrayList<>();
        for (int j : allowedToTakeSet) {
            int reachableIndex = (positionInt - j) - 1;
            if (reachableIndex >= 0) {
                Position reachablePosition = this.positions.get(reachableIndex);
                lowerPositions.add(reachablePosition);
            }
        }
        return lowerPositions;
    }

    private void play() {
        int choice = choosePlayerNumber();
        if (choice == 1) {
            start1PlayerGame();
        } else {
            start2PlayerGame();
        }
    }

    private void start1PlayerGame() {
        Random random = new Random();
        String[] players = {"Player", "Computer"};

        String startingPlayer = players[random.nextInt(players.length)];
        this.gamePosition = this.positions.get(this.matches - 1);

        if (startingPlayer.equals("Player")) {
            System.out.println("Player gets to take the first move!");
            currentPlayer = "Player";
            playerMove();
            currentPlayer = "Computer";

        } else {
            System.out.println("Computer gets to take the first move!");
            currentPlayer = "Computer";
            computerMove();
            currentPlayer = "Player";
        }

        Boolean gameFinished = false;
        while (!gameFinished) {
            System.out.println("number of matches left " + this.gamePosition.getNumberOfMatchesLeft());
            if (currentPlayer.equals("Computer")) {
                computerMove();
            } else {
                playerMove();
            }

            if (this.matches == 1) {
                System.out.println(currentPlayer + " wins!");
                gameFinished = true;
            }

            switchPlayerTurn(players);
        }
    }

    private void switchPlayerTurn(String[] players) {
        if (this.currentPlayer.equals(players[0])) {
            this.currentPlayer = players[1];
        } else {
            this.currentPlayer = players[0];
        }
    }

    private void computerMove() {
        ArrayList<Position> reachablePositions = this.gamePosition.getReachablePositions();
        Random random = new Random();
        if (reachablePositions.size() > 1) {
            this.gamePosition = reachablePositions.get(random.nextInt(reachablePositions.size()));
        } else {
            this.gamePosition = reachablePositions.get(0);
        }
        System.out.println("Computer took " + (this.matches  - this.gamePosition.getNumberOfMatchesLeft()) + " matches." );
        this.matches = this.gamePosition.getNumberOfMatchesLeft();
    }

    private void playerMove() {
        Scanner scanner = new Scanner(System.in);
        Boolean numberChosen = false;
        Integer chosenNumberInt = null;
        while (!numberChosen) {
            System.out.print(this.currentPlayer + ", what number of matches will you take? ");
            String chosenNumber = scanner.nextLine();
            try {
                chosenNumberInt = Integer.parseInt(chosenNumber);
                if (chosenNumberInt < 1 || chosenNumberInt > 4) {
                    throw new Exception();
                }
                numberChosen = true;
            } catch (Exception e) {
                System.out.println("Please choose a number between one and four.");
            }
        }
        this.matches = this.matches - chosenNumberInt;
        this.gamePosition = getPositionGivenMatches();
    }

    private Position getPositionGivenMatches() {
        for (Position position : this.positions) {
            if (position.getNumberOfMatchesLeft() == this.matches) {
                return position;
            }
        }
        return null;
    }

    private void start2PlayerGame() {
        Random random = new Random();
        String[] players = {"Player1", "Player2"};

        String startingPlayer = players[random.nextInt(players.length)];
        this.gamePosition = this.positions.get(this.matches - 1);

        if (startingPlayer.equals("Player1")) {
            System.out.println("Player1 gets to take the first move!");
            currentPlayer = "Player1";
            playerMove();
            currentPlayer = "Player2";

        } else {
            System.out.println("Player2 gets to take the first move!");
            currentPlayer = "Player2";
            playerMove();
            currentPlayer = "Player1";
        }

        Boolean gameFinished = false;
        while (!gameFinished) {
            System.out.println("number of matches left " + this.gamePosition.getNumberOfMatchesLeft());
            playerMove();

            if (this.matches == 1) {
                System.out.println(currentPlayer + " wins!");
                gameFinished = true;
            }

            switchPlayerTurn(players);
        }
    }

    private int choosePlayerNumber() {
        Scanner scanner = new Scanner(System.in);

        String[] playerNumberOptions = {"1 player game", "2 player game"};

        int choiceInt = 0;
        Boolean playerNumberChosen = false;
        while (!playerNumberChosen) {
            System.out.println("Choose a game type: ");
            for (int i = 0 ; i < playerNumberOptions.length; i++ ) {
                System.out.println((i + 1) + ": " + playerNumberOptions[i]);
            }
            System.out.print("choice: ");
            String choice = scanner.nextLine();
            try {
                choiceInt = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Please give one of the shown numbers.");
                continue;
            }
            playerNumberChosen = true;
        }
        return choiceInt;
    }

    public static void main(String[] args) {
        System.out.println("\nSetting up from a upto int\n");
        simpleNim game = new simpleNim(11, 4);
        game.setUpPositions();

        game.play();
    }

}
