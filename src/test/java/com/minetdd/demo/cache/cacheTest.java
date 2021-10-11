package com.minetdd.demo.cache;

import com.minetdd.demo.Weather;
import com.minetdd.demo.repository.WeatherRepository;
import com.minetdd.demo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class cacheTest {

    @MockBean
    WeatherRepository repository;

    @Autowired
    WeatherService service;

    // Se prueba el @cacheable
    @Test
    void cacheTest(){
        given(repository.findById(1L)).willReturn(Optional.of(new Weather(1L, new Date(), "Cali", "Valle del Cauca", 35.7)));

        Weather weather = service.getWeatherById(1L);
        assertNotNull(weather);

        //recall
        service.getWeatherById(1L);
        verify(repository, times(1)).findById(1L);
    }
}
