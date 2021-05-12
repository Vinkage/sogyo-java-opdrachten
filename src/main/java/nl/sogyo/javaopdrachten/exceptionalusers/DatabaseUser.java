package nl.sogyo.javaopdrachten.exceptionalusers;

public interface DatabaseUser {
    Boolean validateSelf() throws InvalidPasswordException;
}
