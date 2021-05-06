package ru.itis.trofimoff.todoapp.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.trofimoff.todoapp.dto.OauthUser;

import java.io.IOException;
import java.util.Objects;

@Service
public class OAuthService {

    private static OkHttpClient client = new OkHttpClient();

    @Autowired
    public static Logger logger;
    private static String accessEndpoint = "https://oauth.vk.com/access_token?client_id=7810780&client_secret=NrTByxV7tSqghYGYeNHx&redirect_uri=http://localhost:8098/sign-in&code=";
    private static String getUserInfo = "https://api.vk.com/method/users.get?v=5.52&access_token=";

    // getting user's access token by another token
    public static JsonNode getAccessJson(String token) {
        Request request = new Request.Builder()
                .url(accessEndpoint + token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode result = mapper.readTree(Objects.requireNonNull(response.body()).string());
            return result;
        } catch (IOException ex) {
            logger.info("Troubles with getting data. Info: {}", ex.getMessage());
            return null;
        }
    }

    // getting user's data
    public static OauthUser getUsersData(JsonNode accessNode) {
        String accessToken = accessNode.get("access_token").asText();
        int userId = accessNode.get("user_id").asInt();
        String userEmail = accessNode.get("email").asText();

        Request request = new Request.Builder()
                .url(getUserInfo + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode result = mapper.readTree(Objects.requireNonNull(response.body()).string());
            JsonNode data = result.get("response").get(0);
            String userName = data.get("first_name").asText();
            String userSoname = data.get("last_name").asText();

            OauthUser user = OauthUser
                                    .builder()
                                    .id(userId)
                                    .email(userEmail)
                                    .name(userName)
                                    .soname(userSoname)
                                    .build();
            return user;
        } catch (IOException ex) {
            logger.info("Troubles with getting data. Info: {}", ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
         // getting user's info
        System.out.println(getUsersData(getAccessJson("51128c51e2d1d40880")));
    }
}
