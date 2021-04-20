package ru.itis.trofimoff.todoapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WeatherDto {
    private String city;
    private String imageLink;
    private int temperature;
    private int humidity;
    private int windSpeed;
    private int pressure;
}
