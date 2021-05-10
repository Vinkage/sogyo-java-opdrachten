package nl.sogyo.javaopdrachten.roborally;

import java.util.ArrayList;

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
