package ru.itis.trofimoff.todoapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OauthUser {
    private int id;
    private String name;
    private String soname;
    private String email;
}
