package nl.sogyo.javaopdrachten.exceptionalusers;

public class ExceptionalUsers {
    private User validUser;
    private User invalidUser;
    public static void main(String[] args) {
        ExceptionalUsers exceptionalUsers = new ExceptionalUsers();

        exceptionalUsers.setExampleUsersToShowOffValidatePassword();

        System.out.println(DatabaseUser.validatePassword("passwordValidateTest"));


    }

    private void setExampleUsersToShowOffValidatePassword() {
        this.validUser = new User("validJacob", "PassWord123");
        this.invalidUser = new User("invalidDorothy", "ilovecats");
    }
}