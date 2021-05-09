package ru.itis.trofimoff.todoapp.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.models.Group;
import ru.itis.trofimoff.todoapp.models.Todo;
import ru.itis.trofimoff.todoapp.security.details.UserDetailsImpl;
import ru.itis.trofimoff.todoapp.services.group.GroupService;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;
import ru.itis.trofimoff.todoapp.services.user.UserService;
import ru.itis.trofimoff.todoapp.utils.pagination.PaginationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    public TodoService todoService;

    @Autowired
    public UserService userService;

    @Autowired
    public GroupService groupService;

    @Autowired
    public PaginationUtil paginationUtil;

    private int pageSize = 5;
    private int currentPage = 0;

    @GetMapping(value = "/main")
    public String getMainPage(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userFromSecurity) {

        request.getSession().setAttribute("currentUser", userService.findByEmail(userFromSecurity.getUsername()).get());

        UserDto currentUser = (UserDto) request.getSession().getAttribute("currentUser");

        pageSize = request.getParameter("size") == null ? pageSize : Integer.parseInt(request.getParameter("size"));
        currentPage = request.getParameter("page") == null ? currentPage : Integer.parseInt(request.getParameter("page"));

        List<Todo> todoObjects = todoService.getUserTodosWithPagination(currentUser.getId(), currentPage, pageSize);
        List<Group> groupObjects = groupService.getAllGroups();

        request.getSession().setAttribute("todos", todoObjects);
        request.getSession().setAttribute("groups", groupObjects);

        int todosAmount = todoService.getUsersTodosAmount(currentUser.getId());
        int pageAmount = paginationUtil.getPageAmount(todosAmount, pageSize);

        request.getSession().setAttribute("size", pageSize);
        request.getSession().setAttribute("pageAmount", pageAmount);
        request.getSession().setAttribute("currentPage", currentPage);

        return "main";
    }
}
