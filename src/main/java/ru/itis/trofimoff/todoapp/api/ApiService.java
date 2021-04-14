package ru.itis.trofimoff.todoapp.api;

import com.fasterxml.jackson.databind.JsonNode;
import ru.itis.trofimoff.todoapp.api.dto.WeatherDto;

public interface ApiService {
    JsonNode getData();
    WeatherDto convertData(JsonNode data);
}
