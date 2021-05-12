package nl.sogyo.javaopdrachten.exceptionalusers;

public class InvalidPasswordException extends Throwable {
    public InvalidPasswordException(String s) {
        super(s);
    }
}
