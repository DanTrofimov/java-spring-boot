package ru.itis.trofimoff.todoapp.exceptions;

public class CantGetUsersDataException extends RuntimeException {
    public CantGetUsersDataException(String message) {
        super(message);
    }
}
