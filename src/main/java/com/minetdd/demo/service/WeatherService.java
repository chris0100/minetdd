package com.minetdd.demo.service;

import com.minetdd.demo.Weather;
import com.minetdd.demo.error.WeatherNotFoundException;
import com.minetdd.demo.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    @Autowired
    WeatherRepository repository;



    @Cacheable("weather")
    public Weather getWeatherById(Long id) {
        return repository.findById(id).orElseThrow(()-> new WeatherNotFoundException("No se ha encontrado el weather : "+ id));
    }

    public List<Weather> getAllWeather() {
        return repository.findAll();
    }

    public Weather saveWeather(Weather weather) {
        return repository.save(weather);
    }

    public Weather updateWeather(Weather weather) {
        return repository.save(weather);
    }
}
