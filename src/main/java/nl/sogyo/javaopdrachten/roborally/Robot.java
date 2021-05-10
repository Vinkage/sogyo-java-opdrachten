package nl.sogyo.javaopdrachten.roborally;

import java.util.ArrayList;

class SpeedIntTooFastException extends Exception {
    public SpeedIntTooFastException(String errorMessage) {
        super(errorMessage);
    }
}

class SpeedIntTooSlowException extends Exception {
    public SpeedIntTooSlowException(String errorMessage) {
        super(errorMessage);
    }
}

public class Robot {
    private int x = 0;
    private int y = 0;
    private String facing = "North";
    private ArrayList<MyFunction> commandQueue= new ArrayList<>();

    public void execute() {
        for (MyFunction command : commandQueue) {
            command.exe();
        }
        commandQueue.clear();
    }

    public interface MyFunction {
        public void exe();
    }

    public Robot() {}

    public Robot(int x, int y, String facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    public void printState() {
        commandQueue.add(
                () -> {
                    System.out.println("Now facing \"" + facing + "\" at (" + x + ", " + y + ")");
                }
        );
    }

    public void forward(int speedInt) {
        try {
            if (speedInt > 3) {
                throw new SpeedIntTooFastException("You provided a too high speedInt argument to Robot.forward");
            } else if (speedInt < 1) {
                throw new SpeedIntTooSlowException("You provided a low high speedInt argument to Robot.forward");
            }

            commandQueue.add(() -> {
                switch (this.facing) {
                    case "North":
                        this.y = this.y + speedInt;
                        break;
                    case "East":
                        this.x = this.x + speedInt;
                        break;
                    case "South":
                        this.y = this.y - speedInt;
                        break;
                    case "West":
                        this.x = this.x - speedInt;
                        break;
                        }
                    }
            );

        } catch (SpeedIntTooFastException e) {
            System.out.println(e);
        } catch (SpeedIntTooSlowException e) {
            System.out.println(e);
        }
    }


    public void forward() {
        commandQueue.add(() -> {
                    switch (this.facing) {
                        case "North":
                            this.y++;
                            break;
                        case "East":
                            this.x++;
                            break;
                        case "South":
                            this.y--;
                            break;
                        case "West":
                            this.x--;
                            break;
                    }
                }
        );
    }

    public void backward() {
        commandQueue.add(
                () -> {
                    switch (this.facing) {
                        case "North":
                            this.y--;
                            break;
                        case "East":
                            this.x--;
                            break;
                        case "South":
                            this.y++;
                            break;
                        case "West":
                            this.x++;
                            break;
                    }

                }
        );
    }

    public void turnRight() {
        commandQueue.add(
                () -> {
                    switch (this.facing) {
                        case "North":
                            this.facing = "East";
                            break;
                        case "East":
                            this.facing = "South";
                            break;
                        case "South":
                            this.facing = "West";
                            break;
                        case "West":
                            this.facing = "North";
                            break;
                    }
                }
        );
    }

    public void turnLeft() {
        commandQueue.add(
                () -> {
                    switch (this.facing) {
                        case "North":
                            this.facing = "West";
                            break;
                        case "West":
                            this.facing = "South";
                            break;
                        case "South":
                            this.facing = "East";
                            break;
                        case "East":
                            this.facing = "North";
                            break;
                    }
                }
        );
    }
}
