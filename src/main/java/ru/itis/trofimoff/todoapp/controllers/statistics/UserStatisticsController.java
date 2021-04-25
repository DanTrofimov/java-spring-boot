package ru.itis.trofimoff.todoapp.controllers.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.trofimoff.todoapp.dto.UserStatisticsDto;
import ru.itis.trofimoff.todoapp.services.user.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserStatisticsController {

    @Autowired
    public UserService userService;

    @GetMapping(value = "/user-statistics", produces = "application/json")
    @ResponseBody
    public UserStatisticsDto getUsersStatistics(HttpServletRequest request) {
        return userService.getUserStatistic(Integer.parseInt(request.getParameter("userId")));
    }

    @PostMapping(value = "/user-statistics")
    public String postUsersStatistics() {
        return "redirect:/main";
    }
}
