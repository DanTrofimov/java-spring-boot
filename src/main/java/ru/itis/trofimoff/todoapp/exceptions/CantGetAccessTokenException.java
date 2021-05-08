package ru.itis.trofimoff.todoapp.exceptions;

public class CantGetAccessTokenException extends RuntimeException {
    public CantGetAccessTokenException(String message) {
        super(message);
    }
}
