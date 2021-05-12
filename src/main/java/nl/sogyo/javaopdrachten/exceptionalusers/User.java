package nl.sogyo.javaopdrachten.exceptionalusers;

public class User implements DatabaseUser {
    private String userName;
    private String passWord;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

}
