package nl.sogyo.javaopdrachten.nim;

public class LosingPosition extends Position {

    public LosingPosition(int numberOfMatchesLeft) {
        super(numberOfMatchesLeft);
    }

    public void addReachablePosition(Position position) {
        if (position instanceof WinningPosition) {
            // System.out.println("    Adding winning position " + position.getNumberOfMatchesLeft());
            super.addReachablePosition(position);
        }
    }

}
