package com.minetdd.demo.repository;

import com.minetdd.demo.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository  extends JpaRepository<Weather, Long> {
    Weather findByCity(String city);
}
