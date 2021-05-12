package nl.sogyo.javaopdrachten.exceptionalusers;

import java.util.Scanner;

public class ExceptionalUsers {
    private User validUser;
    private User invalidUser;

    public static void main(String[] args) {
        ExceptionalUsers exceptionalUsers = new ExceptionalUsers();

        exceptionalUsers.setExampleUsersToShowOffValidator();
        
        UserValidator userValidator = new UserValidator(new User[]{exceptionalUsers.validUser, exceptionalUsers.invalidUser});
        userValidator.validateUsers();

        exceptionalUsers.entryMocker();
    }

    private void entryMocker() {
        String enteredUserName = null;
        String enteredPassWord = null;

        Boolean successfulEntry = false;
        
        Scanner scanner = new Scanner(System.in);

        User user = new User();
        while (!successfulEntry) {
            if (!user.getHasValidUsername()) {
                System.out.print("Enter a username\n> ");
                enteredUserName = scanner.nextLine();
            }
            if (!user.getHasValidPassword()) {
                System.out.print("Enter a password\n> ");
                enteredPassWord = scanner.nextLine();
            }
            user.setUserName(enteredUserName);
            user.setPassWord(enteredPassWord);
            UserValidator userValidator = new UserValidator(user);

            if (userValidator.validateUsers()) {
                successfulEntry = true;
                System.out.println("Registered user " + enteredUserName + ".");
            }
        }
    }


    private void setExampleUsersToShowOffValidator() {
        this.validUser = new User("validJacob", "PassWord123");
        this.invalidUser = new User("invalidDorothy", "ilovecats");
    }

}