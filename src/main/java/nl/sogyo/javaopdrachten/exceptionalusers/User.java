package nl.sogyo.javaopdrachten.exceptionalusers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements DatabaseUser {
    private String userName;
    private String passWord;
    private Boolean hasValidUsername = false;
    private Boolean hasValidPassword = false;

    public User() {}

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassWord(String password) {
        this.passWord = password;
    }

    public String getPassWord() {
        return passWord;
    }

    @Override
    public Boolean validateSelf() throws InvalidPasswordException {
        this.setHasValidUsername(true);
        if (!validatePassword(this.passWord)) {
            throw new InvalidPasswordException("Invalid password was given!");
        }
        this.setHasValidPassword(true);
        return true;
    }

    private static Boolean validatePassword(String password) {
        Matcher upperCaseLetter = Pattern.compile(".*[A-Z].*").matcher(password);
        Matcher lowerCaseLetter = Pattern.compile(".*[a-z].*").matcher(password);
        Matcher number = Pattern.compile(".*[0-9].*").matcher(password);

        return upperCaseLetter.matches() && lowerCaseLetter.matches() && number.matches();
    }

    public Boolean getHasValidUsername() {
        return hasValidUsername;
    }

    public void setHasValidUsername(Boolean hasValidUsername) {
        this.hasValidUsername = hasValidUsername;
    }

    public Boolean getHasValidPassword() {
        return hasValidPassword;
    }

    public void setHasValidPassword(Boolean hasValidPassword) {
        this.hasValidPassword = hasValidPassword;
    }
}