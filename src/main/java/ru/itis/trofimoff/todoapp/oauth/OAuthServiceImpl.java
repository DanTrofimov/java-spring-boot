package ru.itis.trofimoff.todoapp.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.trofimoff.todoapp.models.OauthUser;
import ru.itis.trofimoff.todoapp.repositories.jpa.OauthUserRepository;

import java.io.IOException;
import java.util.Objects;

@Service
public class OAuthServiceImpl implements OauthService {

    @Autowired
    public OauthUserRepository oauthUserRepository;

    private OkHttpClient client = new OkHttpClient();

    @Autowired
    public Logger logger;
    private String accessEndpoint = "https://oauth.vk.com/access_token?client_id=7810780&client_secret=NrTByxV7tSqghYGYeNHx&redirect_uri=http://localhost:8098/oauth&code=";
    private String getUserInfo = "https://api.vk.com/method/users.get?v=5.52&access_token=";

    // getting access data
    public JsonNode getAccessJson(String token) {
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
    public OauthUser getUsersData(JsonNode accessNode) {
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
                                    .serviceId(userId)
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

    public void saveOauthUser(OauthUser user) {
        if (oauthUserRepository.findByServiceId(user.getServiceId()).isEmpty()) oauthUserRepository.save(user);
    }

    public OauthUser getOauthUserByEmail(String email) {
        return oauthUserRepository.getOauthUserByEmail(email).get();
    }
}
