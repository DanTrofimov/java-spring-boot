package ru.itis.trofimoff.todoapp.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class OAuthService {

    private static OkHttpClient client = new OkHttpClient();

    @Autowired
    public static Logger logger;
    private static String accessEndpoint = "https://oauth.vk.com/access_token?client_id=7810780&client_secret=NrTByxV7tSqghYGYeNHx&redirect_uri=http://localhost:8098/sign-in&code=";
    private static String getUserInfo = "https://api.vk.com/method/users.get?v=5.52&access_token=";

    // getting user's access token
    public static String getAccessToken(String token) {
        Request request = new Request.Builder()
                .url(accessEndpoint + token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode result = mapper.readTree(Objects.requireNonNull(response.body()).string());
            System.out.println(result);
            String userId = result.get("user_id").asText();
            String email = result.get("email").asText();
            String accessToken = result.get("access_token").asText();
            // getting user's email & access_token & user_id -> OAuthDto
            System.out.println(email);
            System.out.println(accessToken);
            System.out.println(userId);
            return accessToken;
        } catch (IOException ex) {
            logger.info("Troubles with getting data. Info: {}", ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
         // getting user's info
        System.out.println(getUserInfo + getAccessToken("364fe26bddae6ef261"));
    }
}
