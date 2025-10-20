package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String apikey = "67eb970b906666b611cea13da2778c76";

    private String API = "http://api.weatherstack.com/current?access_key=ACCESS_KEY&query=CITY";


    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("CITY", city).replace("ACCESS_KEY", apikey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }


}
