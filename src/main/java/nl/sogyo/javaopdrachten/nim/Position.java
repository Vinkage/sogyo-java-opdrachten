package nl.sogyo.javaopdrachten.nim;

import java.util.ArrayList;

public class Position {
    private int numberOfMatchesLeft;
    private ArrayList<Position> reachablePositions = new ArrayList<>();

    public Position(int numberOfMatchesLeft) {
        this.numberOfMatchesLeft = numberOfMatchesLeft;
    }

    public int getNumberOfMatchesLeft() { return numberOfMatchesLeft;
    }

    public void addReachablePosition(Position position) {
        this.reachablePositions.add(position);
    }

    public ArrayList<Position> getReachablePositions() {
        return reachablePositions;
    }
}

