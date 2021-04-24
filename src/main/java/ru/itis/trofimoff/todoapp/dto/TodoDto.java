package ru.itis.trofimoff.todoapp.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.trofimoff.todoapp.models.Group;
import ru.itis.trofimoff.todoapp.models.Todo;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    @NotBlank
    private String todoText;
    private int id = 0;

    private Group groupObject;

    public TodoDto(String text) {
        this.todoText = text;
    }

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.todoText = todo.getText();
        this.groupObject = todo.getGroup();
    }
}
