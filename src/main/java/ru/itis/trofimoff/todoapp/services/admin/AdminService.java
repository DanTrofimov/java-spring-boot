package ru.itis.trofimoff.todoapp.services.admin;

import ru.itis.trofimoff.todoapp.dto.AdminTodoDto;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.exceptions.UnknownGroupException;

import java.util.List;

public interface AdminService {
    List<UserDto> getAllUsers();
    void addTodoForSeveralUsers(AdminTodoDto adminTodoDto) throws UnknownGroupException;
}
