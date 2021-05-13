package ru.itis.trofimoff.todoapp.controllers.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.models.Todo;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FilterTodoController {

    @Autowired
    public TodoService todoService;

    @GetMapping(value = "/filter-todos")
    public String getFilterTodo(HttpServletRequest request) {
        int groupId = Integer.parseInt(request.getParameter("group"));
        UserDto currentUser = (UserDto) request.getSession().getAttribute("currentUser");
        List<Todo> todos = todoService.getUserTodosByGroup(currentUser.getId(), groupId);
        request.getSession().setAttribute("todos", todos);
        return "/main";
    }

    @PostMapping(value = "/filter-todos")
    public String postFilterTodo() {
        return "/main";
    }
}
