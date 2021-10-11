package com.minetdd.demo.repository;

import com.minetdd.demo.Weather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static com.minetdd.demo.DataDummy.weather;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class WeatherRepositoryTest {

    @Autowired
    private WeatherRepository repository;

    @Test
    void findById(){
        Optional<Weather> weather = repository.findById(1L);
        assertTrue(weather.isPresent());
    }

    @Test
    void saveWeather() throws ParseException {
        Weather weatherSaved = repository.save(weather());
        Optional<Weather> returnWeather = repository.findById(0L);
        assertTrue(returnWeather.isPresent());
        assertEquals(weatherSaved, returnWeather.get());
    }

    @Test
    void testFindById_NotFound(){
        Optional<Weather> weather = repository.findById(50L);
        assertFalse(weather.isPresent());
    }

    @Test
    void getAllWeather(){
        List<Weather> weatherList = repository.findAll();
        weatherList.forEach(w -> {
            System.out.println(w.getId() + " : : " + w.getCity() + " : : " + w.getTemperature());
        });
        assertEquals(4, weatherList.size());
    }

    @Test
    void updateWeather(){
        Optional<Weather> toBeUpdateWeather = repository.findById(1L);
        toBeUpdateWeather.get().setCity("Jamundi");
        repository.save(toBeUpdateWeather.get());

        Optional<Weather> uptadeWeather = repository.findById(1L);
        assertEquals("Jamundi", uptadeWeather.get().getCity());
    }


}
