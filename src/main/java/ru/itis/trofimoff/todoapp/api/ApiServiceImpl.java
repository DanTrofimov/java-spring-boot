package ru.itis.trofimoff.todoapp.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.trofimoff.todoapp.api.dto.WeatherDto;

import java.io.IOException;
import java.util.Objects;

@Service
public class ApiServiceImpl implements ApiService {

    private String endpoint = "https://api.openweathermap.org/data/2.5/weather?q=Kazan&units=metric&APPID=";

    private String imgUrl = "http://openweathermap.org/img/w/";

    private String imageFormat = ".png";

    private String apiKey = "182c7bbcc2c022c0b3f13b3ccf9198cc";

    private OkHttpClient client = new OkHttpClient();

    @Autowired
    public Logger logger;

    @Override
    public JsonNode getData() {

        Request request = new Request.Builder()
                .url(endpoint + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readTree(Objects.requireNonNull(response.body()).string());
        } catch (IOException ex) {
            logger.info("Troubles with getting data. Info: {}", ex.getMessage());
            return null;
        }
    }

    @Override
    public WeatherDto convertData(JsonNode data) {
        ArrayNode arrayNode = (ArrayNode) data.get("weather");
        return WeatherDto
                .builder()
                .city(data.get("name").asText())
                .imageLink(imgUrl + arrayNode.get(0).get("icon").asText() + imageFormat)
                .temperature(data.at("/main/temp").asInt())
                .humidity(data.at("/main/humidity").asInt())
                .pressure(data.at("/main/pressure").asInt())
                .windSpeed(data.at("/wind/speed").asInt())
                .build();
    }
}
