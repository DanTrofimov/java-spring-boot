package ru.itis.trofimoff.todoapp.controllers.admin;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.trofimoff.todoapp.dto.AdminTodoDto;
import ru.itis.trofimoff.todoapp.exceptions.UnknownGroupException;
import ru.itis.trofimoff.todoapp.services.admin.AdminService;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;

import javax.validation.Valid;

@Controller
public class AdminAddTodoController {

    @Autowired
    public TodoService todoService;

    @Autowired
    public AdminService adminService;

    @Autowired
    public Logger logger;

    @RequestMapping(value = "/admin-add", method = RequestMethod.POST)
    public String postAdminAddPage(@Valid AdminTodoDto adminDto, BindingResult bindingResult){

        if (!bindingResult.hasErrors()) {
            try {
                adminService.addTodoForSeveralUsers(adminDto);
            } catch (UnknownGroupException ex) {
                logger.info("Get a wrong group. Info: {}", ex.getMessage());
            }
        }

        return "redirect:/admin-add";
    }

    @RequestMapping(value = "/admin-add", method = RequestMethod.GET)
    public String getAdminAddPage(){
        return "/admin-add";
    }
}
