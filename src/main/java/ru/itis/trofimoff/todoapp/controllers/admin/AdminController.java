package ru.itis.trofimoff.todoapp.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.trofimoff.todoapp.security.details.UserDetailsImpl;
import ru.itis.trofimoff.todoapp.services.admin.AdminService;
import ru.itis.trofimoff.todoapp.services.user.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    public AdminService adminService;

    @Autowired
    public UserService userService;

    @GetMapping(value = "/admin")
    public String getAdminPage(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userFromSecurity){

        request.getSession().setAttribute("currentUser", userService.findByEmail(userFromSecurity.getUsername()).get());

        request.getSession().setAttribute("allUsers", adminService.getAllUsers());
        return "admin";
    }
}
