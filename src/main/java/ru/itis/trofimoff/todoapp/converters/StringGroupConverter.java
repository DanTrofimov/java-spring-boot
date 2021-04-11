package ru.itis.trofimoff.todoapp.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.itis.trofimoff.todoapp.exceptions.UnknownGroupException;
import ru.itis.trofimoff.todoapp.models.Group;
import ru.itis.trofimoff.todoapp.services.group.GroupService;

import java.util.Optional;

@Component
public class StringGroupConverter implements Converter<String, Group> {

    @Autowired
    public GroupService groupService;

    @Override
    public Group convert(String str) throws UnknownGroupException {
        str = str.equals("user") ? "users" : str;
        Optional<Group> currentGroup = groupService.findByName(str);
        if (currentGroup.isPresent()) {
            return currentGroup.get();
        } else {
            throw new UnknownGroupException("Can't find such a group");
        }
    }
}
