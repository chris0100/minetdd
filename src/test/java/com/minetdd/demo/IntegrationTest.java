package com.minetdd.demo;

import com.minetdd.demo.error.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.minetdd.demo.DataDummy.weather;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    @Test
    void getWeatherById(){
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Weather> response = restTemplate.exchange(
                "/weather/1", HttpMethod.GET, entity, Weather.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
        assertEquals("Cali", Objects.requireNonNull(response.getBody()).getCity());
    }

    @Test
    void getWeatherByCity(){
        //HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Weather> responseEntity = restTemplate.getForEntity(
                "/weather?city=Cali", Weather.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Cali", Objects.requireNonNull(responseEntity.getBody()).getCity());
    }


    @Test
    void saveWeather() throws ParseException {
        ResponseEntity<Weather> response = restTemplate.postForEntity("/weather", weather(), Weather.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getId());
    }


    @Test
    void weatherNotFound(){
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ApiError> response = restTemplate.exchange(
                "/weather/10", HttpMethod.GET, entity, ApiError.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void getAllWeather(){
        ResponseEntity<List<Weather>> response = restTemplate.exchange(
                "/weather", HttpMethod.GET, null, new ParameterizedTypeReference<List<Weather>>() {}
        );
        List<Weather> weatherList = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert weatherList != null;
        assertEquals(4, weatherList.size());
    }


    @Test
    void updateWeather() throws ParseException {
        Weather saveWeather = weather();
        ResponseEntity<Weather> saveResponse = restTemplate.postForEntity("/weather", saveWeather, Weather.class);
        Weather updateWeather = saveResponse.getBody();
        updateWeather.setCity("Jamundi");

        HttpEntity<Weather> entity = new HttpEntity<>(updateWeather, headers);
        ResponseEntity<Weather> response = restTemplate.exchange(
                "/weather", HttpMethod.PUT, entity, Weather.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Jamundi", Objects.requireNonNull(response.getBody()).getCity());
    }


    @Test
    void deleteWeather(){
        ResponseEntity<Weather[]> respuesta = restTemplate.getForEntity("/weather", Weather[].class);
        List<Weather> weatherList = Arrays.asList(Objects.requireNonNull(respuesta.getBody()));
        assertEquals(4, weatherList.size());

        restTemplate.delete("/weather/2");

        respuesta = restTemplate.getForEntity("/weather", Weather[].class);
        weatherList = Arrays.asList(Objects.requireNonNull(respuesta.getBody()));
        assertEquals(3, weatherList.size());
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }





    @Test
    void main(){
        DemoApplication.main(new String[] {});
    }
}
