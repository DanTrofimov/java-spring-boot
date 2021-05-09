package ru.itis.trofimoff.todoapp.controllers.admin;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.trofimoff.todoapp.dto.AdminTodoDto;
import ru.itis.trofimoff.todoapp.exceptions.UnknownGroupException;
import ru.itis.trofimoff.todoapp.services.admin.AdminService;

import javax.validation.Valid;

@Controller
public class AdminAddTodoController {

    @Autowired
    public AdminService adminService;

    @Autowired
    public Logger logger;

    @PostMapping(value = "/admin-add")
    public String postAdminAddPage(@Valid AdminTodoDto adminDto, BindingResult bindingResult){

        if (!bindingResult.hasErrors()) {
            try {
                adminService.addTodoForSeveralUsers(adminDto);
            } catch (UnknownGroupException ex) {
                logger.error("Get a wrong group. Info: {}", ex.getMessage());
            }
        }

        return "redirect:/admin-add";
    }

    @GetMapping(value = "/admin-add")
    public String getAdminAddPage(){
        return "/admin-add";
    }
}
