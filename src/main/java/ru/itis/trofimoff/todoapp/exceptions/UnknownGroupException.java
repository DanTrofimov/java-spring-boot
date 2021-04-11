package ru.itis.trofimoff.todoapp.exceptions;

public class UnknownGroupException extends RuntimeException {
    public UnknownGroupException() {
        super();
    }

    public UnknownGroupException(String message) {
        super(message);
    }
}
