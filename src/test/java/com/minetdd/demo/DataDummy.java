package com.minetdd.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DataDummy {

    public static List<Weather> allWeather() throws ParseException {
        List<Weather> allWeather = new ArrayList<>();
        allWeather.add(new Weather(1L, new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-20"), "Cali", "Valle del Cauca", 25.8));
        allWeather.add(new Weather(2L, new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-20"), "Palmira", "Valle del Cauca", 32.8));
        allWeather.add(new Weather(3L, new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-20"), "Caicedonia", "Valle del Cauca", 20.8));
        allWeather.add(new Weather(4L, new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-20"), "Cartago", "Valle del Cauca", 35.8));
        return allWeather;
    }


    public static Weather weather() throws ParseException {
        return Weather.builder()
                .date(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-28"))
                .city("Cajamarca")
                .state("Valle del Cauca")
                .temperature(30.1)
                .build();
    }

}
