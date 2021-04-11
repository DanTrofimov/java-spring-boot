package ru.itis.trofimoff.todoapp.controllers.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;

@Controller
public class AddTodoControllers {

    @Autowired
    public TodoService todoService;

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String getAddTodo() {
        return "redirect:/main";
    }
}
