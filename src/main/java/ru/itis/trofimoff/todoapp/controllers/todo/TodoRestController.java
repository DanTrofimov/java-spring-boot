package ru.itis.trofimoff.todoapp.controllers.todo;
// for tests disable CSRF
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.trofimoff.todoapp.dto.TodoDto;
import ru.itis.trofimoff.todoapp.exceptions.UnknownGroupException;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;

import java.util.List;

@RestController
public class TodoRestController {
    @Autowired
    public TodoService todoService;

    @Autowired
    public Logger logger;

    @GetMapping("/todos")
    public ResponseEntity<List<TodoDto>> getTodos(@RequestParam Integer userId) {
        return ResponseEntity.ok(todoService.getUserTodos(userId));
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoDto> postTodo(@RequestParam Integer userId, @RequestParam String todoText, @RequestParam String group) {
        ResponseEntity<TodoDto> resultTodo = null;
        try {
            resultTodo = ResponseEntity.ok(todoService.addTodoRest(todoText, userId, group));
        } catch (UnknownGroupException ex) {
            logger.info("Get a wrong group. Info: {}", ex.getMessage());
        }
        return resultTodo;
    }

    @PutMapping("/todos")
    public ResponseEntity<TodoDto> putTodo(@RequestBody  TodoDto todoDto) {
        todoService.updateTodo(todoDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/todos")
    public ResponseEntity<?> deleteTodo(@RequestParam Integer todoId, @RequestParam Integer userId) {
        todoService.deleteTodo(todoId, userId);
        return ResponseEntity.ok().build();
    }
}
