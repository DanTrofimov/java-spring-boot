package ru.itis.trofimoff.todoapp.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import ru.itis.trofimoff.todoapp.models.OauthUser;

import javax.servlet.http.HttpServletRequest;

public interface OauthService {
    JsonNode getAccessJson(String token);
    OauthUser getUsersData(JsonNode accessNode);
    void saveOauthUser(OauthUser user);
    OauthUser getOauthUserByEmail(String email);
    String getEncodedPass(String pass);
    void setAuthentication(HttpServletRequest request, String email);
}