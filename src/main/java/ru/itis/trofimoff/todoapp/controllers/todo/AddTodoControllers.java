package ru.itis.trofimoff.todoapp.controllers.todo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.trofimoff.todoapp.dto.TodoDto;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.exceptions.UnknownGroupException;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AddTodoControllers {

    @Autowired
    public TodoService todoService;

    @Autowired
    public Logger logger;

    @GetMapping(value = "/add-todo")
    public String getAddTodo() {
        return "redirect:/main";
    }

    @PostMapping(value = "/add-todo")
    public String postAddTodo(HttpServletRequest request, @Valid @ModelAttribute TodoDto todoDto, BindingResult bindingResult) {
        UserDto currentUser = (UserDto) request.getSession().getAttribute("currentUser");
        if (!bindingResult.hasErrors()) {
            try {
                todoService.addUsersTodo(todoDto, currentUser.getId(), currentUser.getRole().toLowerCase());
            } catch (UnknownGroupException ex) {
                logger.info("Get a wrong group. Info: {}", ex.getMessage());
            }
        }
        return "redirect:/main";
    }

}
