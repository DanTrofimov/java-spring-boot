package ru.itis.trofimoff.todoapp.controllers.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.trofimoff.todoapp.dto.TodoDto;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HandleTodoController {

    @Autowired
    public TodoService todoService;

    @RequestMapping(value = "/handle-todo", method = RequestMethod.GET)
    public String getHandleTodo(HttpServletRequest request) {
        return "redirect:/main";
    }
}
