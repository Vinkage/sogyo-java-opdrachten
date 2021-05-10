package nl.sogyo.javaopdrachten.roborally;

public class RoboRally {

    public void rallyAllMethodsTestDrive() {
        Robot myAwesomeBot = new Robot(1, 0, "North");
        myAwesomeBot.turnLeft();
        myAwesomeBot.printState();
        myAwesomeBot.turnLeft();
        myAwesomeBot.printState();
        myAwesomeBot.forward(3);
        myAwesomeBot.printState();
        myAwesomeBot.turnRight();
        myAwesomeBot.printState();
        myAwesomeBot.backward();
        myAwesomeBot.printState();
        myAwesomeBot.forward();
        myAwesomeBot.printState();
        myAwesomeBot.execute();
    }

    private void rally360BothWays() {
        Robot myAwesomeBot = new Robot(1, 0, "North");
        System.out.println("360 Rightways");
        myAwesomeBot.turnRight();
        myAwesomeBot.printState();
        myAwesomeBot.turnRight();
        myAwesomeBot.printState();
        myAwesomeBot.turnRight();
        myAwesomeBot.printState();
        myAwesomeBot.turnRight();
        myAwesomeBot.printState();
        myAwesomeBot.execute();

        System.out.println("360 Leftways");
        myAwesomeBot.turnLeft();
        myAwesomeBot.printState();
        myAwesomeBot.turnLeft();
        myAwesomeBot.printState();
        myAwesomeBot.turnLeft();
        myAwesomeBot.printState();
        myAwesomeBot.turnLeft();
        myAwesomeBot.printState();
        myAwesomeBot.execute();
    }

    private void rallyForwardBackward() {
        Robot myAwesomeBot = new Robot(1, 0, "North");
        myAwesomeBot.forward();
        myAwesomeBot.printState();
        myAwesomeBot.forward(1);
        myAwesomeBot.printState();
        myAwesomeBot.forward(2);
        myAwesomeBot.printState();
        myAwesomeBot.forward(3);
        myAwesomeBot.printState();
        myAwesomeBot.backward();
        myAwesomeBot.printState();
        myAwesomeBot.execute();
    }

    public static void main(String args[]) {
        RoboRally roboRally = new RoboRally();
        System.out.println("Showing all methods of a robot!");
        System.out.println();
        roboRally.rallyAllMethodsTestDrive();
        System.out.println();
        System.out.println("Showing all directions by doing a 360 both ways!");
        System.out.println();
        roboRally.rally360BothWays();
        System.out.println();
        System.out.println("Showing all movements by doing a forward backward motion!");
        System.out.println();
        roboRally.rallyForwardBackward();
        System.out.println();
    }
}
