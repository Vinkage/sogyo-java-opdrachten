package nl.sogyo.javaopdrachten.exceptionalusers;

import java.util.ArrayList;
import java.util.Arrays;

public class UserValidator {
    private ArrayList<User> users;

    public UserValidator(User[] users) {
        this.users = new ArrayList<User>(Arrays.asList(users));
    }

    public UserValidator(User user) {
        this.users = new ArrayList<>();
        this.users.add(user);
    }

    public boolean validateUsers() {
        ArrayList<Boolean> results = new ArrayList<>();

        for (User user: users) {
            try {
                if (!(user instanceof DatabaseUser)) {
                    throw new UserIsNotADataBaseUserException("Users should implement the DatabaseUser interface.");
                }
                results.add(user.validateSelf());
                return !results.contains(false);
            } catch (UserIsNotADataBaseUserException e) {
                e.printStackTrace();
            } catch (InvalidPasswordException e) {
                System.out.println("This is not a valid password. Please enter a stronger password.");
            }
        }
        return false;
    }
}
