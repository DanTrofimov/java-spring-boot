package ru.itis.trofimoff.todoapp.models;

import lombok.Data;

@Data
public class UserStatistics {
    private int allTodos;
    private int doneTodos;
    private int needTodo;

    public UserStatistics(int allTodos, int doneTodos) {
        this.allTodos = allTodos;
        this.doneTodos = doneTodos;
        this.needTodo = allTodos - doneTodos;
    }
}
