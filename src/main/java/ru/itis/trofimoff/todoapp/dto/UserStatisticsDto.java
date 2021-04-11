package ru.itis.trofimoff.todoapp.dto;

import lombok.Data;

@Data
public class UserStatisticsDto {
    private int allTodos;
    private int doneTodos;
    private int needTodo;

    public UserStatisticsDto(int allTodos, int doneTodos) {
        this.allTodos = allTodos;
        this.doneTodos = doneTodos;
        this.needTodo = allTodos - doneTodos;
    }
}