package ru.itis.trofimoff.todoapp.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.trofimoff.todoapp.dto.SignInFormDto;
import ru.itis.trofimoff.todoapp.models.OauthUser;
import ru.itis.trofimoff.todoapp.models.User;
import ru.itis.trofimoff.todoapp.oauth.OauthService;
import ru.itis.trofimoff.todoapp.services.user.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignInController {

    @Autowired
    public UserService userService;

    @Autowired
    public OauthService oauthService;

    @GetMapping(value = "/sign-in")
    public String getSignInPage(Model model, HttpServletRequest request){

        model.addAttribute("signInFormDto", new SignInFormDto());
        String errorText = request.getParameter("error");
        if (errorText != null) {
            model.addAttribute("signInError", errorText);
        }

        return "sign-in";
    }

    @GetMapping(value = "/oauth")
    public String getOauthPage(HttpServletRequest request) {

        // get code
        String code = request.getParameter("code");

        // here we can get all user's info
        OauthUser oauthUser = oauthService.getUsersData(oauthService.getAccessJson(code)); // field user is null
        User user = userService.saveForOauth(new User(oauthUser)); // get filled user
        oauthUser.setUser(user); // full filled oauth user
        oauthService.saveOauthUser(oauthUser);

        // adding user's session
        oauthService.setAuthentication(request, oauthUser.getEmail());

        return "redirect:/main";
    }
}
