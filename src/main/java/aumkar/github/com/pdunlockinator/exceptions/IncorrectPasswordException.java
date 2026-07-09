package aumkar.github.com.pdunlockinator.exceptions;

public class IncorrectPasswordException extends IllegalArgumentException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
