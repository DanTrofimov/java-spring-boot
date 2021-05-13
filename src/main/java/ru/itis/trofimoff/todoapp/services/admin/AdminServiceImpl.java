package ru.itis.trofimoff.todoapp.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.trofimoff.todoapp.dto.AdminTodoDto;
import ru.itis.trofimoff.todoapp.dto.TodoDto;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.exceptions.UnknownGroupException;
import ru.itis.trofimoff.todoapp.models.Group;
import ru.itis.trofimoff.todoapp.services.group.GroupService;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;
import ru.itis.trofimoff.todoapp.services.user.UserService;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private GroupService groupService;

    @Override
    public List<UserDto> getAllUsers() {
        return userService.findAllDefaultUsers();
    }

    @Override
    public void addTodoForSeveralUsers(AdminTodoDto adminDto) throws UnknownGroupException {

        if (adminDto.getTodoText().equals("") || adminDto.getUsers() == null) return;
        TodoDto adminTodoDto = new TodoDto(adminDto.getTodoText());
        int[] users = new int[adminDto.getUsers().length];
        Group adminGroup = groupService.getGroupById(2).get(); // admin, changed _todo's group

        for (int i = 0; i < users.length; i++) {
            users[i] = Integer.parseInt(adminDto.getUsers()[i]);
        }

        // got _todo's id
        todoService.addTodo(adminTodoDto, adminGroup);

        for (int i = 0; i < users.length; i++) {
            todoService.addUsersTodo(adminTodoDto, users[i], "admin");
        }
    }
}
