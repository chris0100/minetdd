package com.minetdd.demo.controller;

import com.minetdd.demo.Weather;
import com.minetdd.demo.error.WeatherNotFoundException;
import com.minetdd.demo.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/weather")
@Slf4j
public class WeatherApiRestController {

    @Autowired
    WeatherService service;

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getWeatherById(@PathVariable Long id)throws WeatherNotFoundException {
        Weather weather = service.getWeatherById(id);
        return new ResponseEntity(weather, HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllWeather(){
        List<Weather> weatherList = service.getAllWeather();
        return new ResponseEntity(weatherList, HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity saveWeather(@Valid @RequestBody Weather weather){
        return new ResponseEntity(service.saveWeather(weather), HttpStatus.CREATED);
    }

    @PutMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity updateWeather(@Valid @RequestBody Weather weather){
        return new ResponseEntity(service.updateWeather(weather), HttpStatus.CREATED);
    }


}
