package net.engineeringdigest.journalApp.service;

import WheatherResponse.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constant.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;
    @Value("${weather.api.key}")
    private String apiKey ;

    @Autowired
    private RedisService redisService;
    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_key_" + city, WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else{
            String weatherApiUrl = appCache.APP_CACHE.get(Enums.WEATHER_API);
            String finalApi = weatherApiUrl.replace(Enums.CITY, city).replace(Enums.API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body!=null){
                redisService.set("weather_key_" + city,body,300l);
            }
            return body;
        }

    }
}
