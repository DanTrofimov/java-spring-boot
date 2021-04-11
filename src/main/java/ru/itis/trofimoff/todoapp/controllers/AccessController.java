package ru.itis.trofimoff.todoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {
    @GetMapping("/access-denied")
    public String getAccessDenied(Model model) {
        model.addAttribute("showError", "Sorry, access denied");
        return "error";
    }
}
