package com.minetdd.demo.service;

import com.minetdd.demo.Weather;
import com.minetdd.demo.error.WeatherNotFoundException;
import com.minetdd.demo.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.minetdd.demo.DataDummy.allWeather;
import static com.minetdd.demo.DataDummy.weather;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.MockitoAnnotations.openMocks;

class WeatherServiceTest {

    @Mock
    private WeatherRepository repository;

    @InjectMocks
    private WeatherService service;

    @Captor
    ArgumentCaptor<Weather> type;

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }

    @Test
    void getWeatherById(){
        given(repository.findById(1L)).willReturn(Optional.of(new Weather(1L, new Date(), "Cali", "Valle del Cauca", 36.5)));

        Weather weather = service.getWeatherById(1L);
        assertNotNull(weather);
        assertEquals("Cali", weather.getCity());
        assertEquals("Valle del Cauca", weather.getState());
    }

    @Test
    void getWeatherById_NotFound(){
        given(repository.findById(1L)).willThrow(new WeatherNotFoundException("No encontrado"));
        assertThrows(WeatherNotFoundException.class, () -> service.getWeatherById(1L));
    }

    @Test
    void getAllWeather() throws ParseException {
        given(repository.findAll()).willReturn(allWeather());

        List<Weather> weatherList = service.getAllWeather();
        assertEquals(4, weatherList.size());
    }


    @Test
    void saveWeather() throws ParseException {
        given(repository.save(any())).willReturn(weather());

        Weather savedWeather = service.saveWeather(weather());

        assertEquals("Cajamarca", savedWeather.getCity());
    }


    @Test
    void updateWeather() throws ParseException {
        given(repository.save(any())).willReturn(allWeather().get(0));

        Weather savedWeather = service.updateWeather(allWeather().get(0));

        then(repository).should().save(type.capture());
        assertEquals("Cali", savedWeather.getCity());
        assertTrue(type.getValue().getId() != null);
    }



}
