package ru.itis.trofimoff.todoapp.controllers.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.trofimoff.todoapp.dto.TodoDto;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class TodoRestController {
    @Autowired
    public TodoService todoService;

    @PostMapping(value = "/handle-todo")
    public String postHandleTodo(HttpServletRequest request) {
        String text = request.getParameter("change-todo-text");
        int todoId = Integer.parseInt(request.getParameter("todo-id"));
        UserDto currentUser = (UserDto) request.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();
        switch (request.getParameter("todo-action")) {
            case "change":
                if (!text.trim().equals("")) {
                    TodoDto todoDto = new TodoDto(text);
                    todoDto.setId(todoId);
                    todoService.updateTodo(todoDto);
                }
                break;
            case "remove":
                todoService.deleteTodo(todoId, userId);
                break;
        }
        return "redirect:/main";
    }

    @PostMapping(value = "/add-todo")
    public String postAddTodo(HttpServletRequest request, @Valid TodoDto todoDto, BindingResult bindingResult) {
        UserDto currentUser = (UserDto) request.getSession().getAttribute("currentUser");
        if (!bindingResult.hasErrors()) {
            todoService.addUsersTodo(todoDto, currentUser.getId(), currentUser.getRole().toLowerCase());
        }
        return "redirect:/main";
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoDto>> getTeachers(@PathVariable Integer userId) {
        return ResponseEntity.ok(todoService.getUserTodos(userId));
    }
}
