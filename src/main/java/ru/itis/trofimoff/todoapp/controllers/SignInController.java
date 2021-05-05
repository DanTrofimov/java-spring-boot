package ru.itis.trofimoff.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.trofimoff.todoapp.dto.SignInFormDto;
import ru.itis.trofimoff.todoapp.services.user.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignInController {

    @Autowired
    public UserService userService;

    @GetMapping(value = "/sign-in")
    public String getSignInPage(Model model, HttpServletRequest request){
        // get code
        String code = request.getParameter("code");
        model.addAttribute("code", code);

        model.addAttribute("signInFormDto", new SignInFormDto());
        String errorText = request.getParameter("error");
        if (errorText != null) {
            model.addAttribute("signInError", errorText);
        }
        return "sign-in";
    }
}
