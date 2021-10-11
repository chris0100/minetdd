package com.minetdd.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "weather")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(name = "date", nullable = false)
        @NotNull(message = "Date can not be null")
        @DateTimeFormat(pattern="yyyy-MM-dd")
        @Temporal(TemporalType.DATE)
        @JsonFormat(pattern="yyyy-MM-dd", timezone = "America/Bogota")
        private Date date;


        private String city;
        private String state;
        private Double temperature;


}
