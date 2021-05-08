package ru.itis.trofimoff.todoapp.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import ru.itis.trofimoff.todoapp.exceptions.CantGetAccessTokenException;
import ru.itis.trofimoff.todoapp.exceptions.CantGetUsersDataException;
import ru.itis.trofimoff.todoapp.models.OauthUser;
import ru.itis.trofimoff.todoapp.repositories.jpa.OauthUserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Service
public class OAuthServiceImpl implements OauthService {

    @Autowired
    public OauthUserRepository oauthUserRepository;

    @Qualifier("customDetailsService")
    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public Logger logger;

    private OkHttpClient client = new OkHttpClient();
    @Value(value = "${vk.api.endpoint.get.access}")
    private String accessEndpoint;
    @Value(value = "${vk.api.endpoint.access}")
    private String getUserInfo;

    // getting access data
    @Override
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
            throw new CantGetAccessTokenException("Endpoint access denied");
        }
    }

    // getting user's data
    @Override
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
            throw new CantGetUsersDataException("Access token denied");
        }
    }

    @Override
    public void saveOauthUser(OauthUser user) {
        if (oauthUserRepository.findByServiceId(user.getServiceId()).isEmpty()) oauthUserRepository.save(user);
    }

    @Override
    public OauthUser getOauthUserByEmail(String email) {
        return oauthUserRepository.getOauthUserByEmail(email).get();
    }

    @Override
    public String getEncodedPass(String pass) {
        return passwordEncoder.encode(pass);
    }

    @Override
    public void setAuthentication(HttpServletRequest request, String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
