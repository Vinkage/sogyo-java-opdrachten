package nl.sogyo.javaopdrachten.exceptionalusers;

public class UserIsNotADataBaseUserException extends Throwable {
    public UserIsNotADataBaseUserException(String s) {
        super(s);
    }
}
