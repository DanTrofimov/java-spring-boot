package ru.itis.trofimoff.todoapp.services.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.trofimoff.todoapp.dto.TodoDto;
import ru.itis.trofimoff.todoapp.exceptions.UnknownGroupException;
import ru.itis.trofimoff.todoapp.models.Group;
import ru.itis.trofimoff.todoapp.models.Todo;
import ru.itis.trofimoff.todoapp.repositories.jpa.GroupRepository;
import ru.itis.trofimoff.todoapp.repositories.jpa.TodoRepository;
import ru.itis.trofimoff.todoapp.repositories.jpa.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUsersTodo(TodoDto todoDto, int userId, String rights) throws UnknownGroupException {
        Todo todo = new Todo(todoDto);
        if (todo.getText().trim().equals("")) return;
        Group group;
        rights = rights.equals("user") ? "users" : rights;
        Optional<Group> optionalGroup = groupRepository.findByName(rights);
        if (optionalGroup.isPresent()) {
            group = optionalGroup.get();
        } else {
            throw new UnknownGroupException("Can't find such a group");
        }
        todo.setGroup(group);
        Todo generatedTodo = todoRepository.save(todo);
        todoRepository.insertTodoIntoUsersTodo(userId, generatedTodo.getId());
        userRepository.incrementUserStatAll(userId);
    }

    @Override
    public void deleteTodo(int todoId, int userId) {
        todoRepository.removeUserBinding(userId, todoId);
        todoRepository.removeById(todoId);
        userRepository.incrementUserStatDone(userId);
    }

    @Override
    public void addTodo(TodoDto todoDto, Group group) {
        Todo todo = new Todo(todoDto);
        if (!todo.getText().trim().equals("")) {
            todoRepository.save(todo);
        }
        todoDto.setId(todo.getId());
    }

    @Override
    public List<TodoDto> getUserTodos(int userId) {
        List<TodoDto> resultList = new ArrayList<>();
        todoRepository.getUsersTodo(userId).forEach(todo -> resultList.add(new TodoDto(todo)));
        return resultList;
    }

    @Override
    public List<Todo> getUserTodosWithPagination(int userId, int page, int size) {
        int offset = size * page;
        return this.todoRepository.getUsersTodoWithPagination(userId, size, offset); // id, limit, offset
    }

    @Override
    public List<Todo> getUserTodosByGroup(int userId, int groupId) {
        return todoRepository.getUsersTodoByGroup(userId, groupId);
    }

    @Override
    public int getUsersTodosAmount(int userId) {
        return this.todoRepository.getUsersTodosAmount(userId);
    }

    @Override
    public void updateTodo(TodoDto todoDto) {
        Todo todo = new Todo(todoDto);
        todoRepository.update(todo.getText(), todo.getId());
    }

    // for REST controller
    @Override
    public TodoDto addTodoRest(String todoText, int userId, String rights) throws UnknownGroupException {
        Todo todo = new Todo(todoText);
        Group group;
        rights = rights.equals("user") ? "users" : rights;
        Optional<Group> optionalGroup = groupRepository.findByName(rights);
        if (optionalGroup.isPresent()) {
            group = optionalGroup.get();
        } else {
            throw new UnknownGroupException("Can't find such a group");
        }
        todo.setGroup(group);
        Todo generatedTodo = todoRepository.save(todo);
        todoRepository.insertTodoIntoUsersTodo(userId, generatedTodo.getId());
        userRepository.incrementUserStatAll(userId);
        return new TodoDto(generatedTodo);
    }
}
