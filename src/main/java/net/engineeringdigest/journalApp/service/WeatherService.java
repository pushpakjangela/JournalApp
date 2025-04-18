package net.engineeringdigest.journalApp.service;

import WheatherResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WheatherService {

    @Autowired
    private RestTemplate restTemplate;
    private static  final String API_KEY = "7e5de6a876e1ef3c99569999dcc53814";
    private static  final  String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    public WeatherResponse getWheather(String city){
         String finalApi= API.replace("CITY",city).replace("API_KEY",API_KEY);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
