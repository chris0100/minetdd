package com.minetdd.demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minetdd.demo.Weather;
import com.minetdd.demo.error.WeatherNotFoundException;
import com.minetdd.demo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.minetdd.demo.DataDummy.allWeather;
import static com.minetdd.demo.DataDummy.weather;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WeatherApiRestController.class)
@ActiveProfiles("test")
class WeatherApiRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WeatherService service;

    private static final ObjectMapper om = new ObjectMapper();


    @Test
    void testWeatherEndpointWithGETById() throws Exception {

        given(service.getWeatherById(anyLong())).willReturn(allWeather().get(0));


        mockMvc.perform(get("/weather/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("city").value("Cali"))
                .andExpect(jsonPath("state").value("Valle del Cauca"));
    }

    @Test
    void weather_NotFound() throws Exception {
        given(service.getWeatherById(anyLong())).willThrow(new WeatherNotFoundException("No encontrado"));

        mockMvc.perform(get("/weather/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testAllWeather() throws Exception {
        given(service.getAllWeather()).willReturn(allWeather());

        List<Weather> actualRecords = om.readValue(mockMvc.perform(get("/weather"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(allWeather().size())))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andReturn().getResponse().getContentAsString(), new TypeReference<List<Weather>>() {
        });

        for (int i = 0; i < allWeather().size(); i++) {
            assertTrue(new ReflectionEquals(allWeather().get(i).getCity()).matches(actualRecords.get(i).getCity()));
        }

        // tip comparacion por ciudad y luego por temperatura
        actualRecords.sort(Comparator.comparing(Weather::getCity).thenComparing(Weather::getTemperature));
        for (Weather obj : actualRecords) {
            System.out.println(obj);
        }
    }


    @Test
    void saveWeather() throws Exception {
        given(service.saveWeather(any())).willReturn(weather());

        mockMvc.perform(
                post("/weather")
                        .content(new ObjectMapper().writeValueAsString(weather()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("city").value("Cajamarca"))
                .andDo(print());
    }


    @Test
    void updateWeather() throws Exception {
        given(service.updateWeather(any())).willReturn(allWeather().get(0));

        mockMvc.perform(put("/weather")
        .content(new ObjectMapper().writeValueAsString(allWeather().get(0)))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andDo(print());
    }


    @Test
    void testBadRequest() throws Exception {
        given(service.updateWeather(any())).willReturn(weather());

        mockMvc.perform(post("/weather")
                .content(new ObjectMapper().writeValueAsString("weather"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }




}
