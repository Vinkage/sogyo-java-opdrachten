package nl.sogyo.javaopdrachten.nim;

public class WinningPosition extends Position {

    public WinningPosition(int numberOfMatchesLeft) {
        super(numberOfMatchesLeft);
    }

    public void addReachablePosition(Position position) {
        if (position instanceof LosingPosition) {
            // System.out.println("    Adding losing position " + position.getNumberOfMatchesLeft());
            super.addReachablePosition(position);
        }
    }
}
