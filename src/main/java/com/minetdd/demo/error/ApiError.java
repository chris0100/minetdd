package com.minetdd.demo.error;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ApiError {


    private String code;
    private String message;
    private String url;
    private Map<String, String> validationErrors;

    public ApiError(String code, String message, String url) {
        this.code = code;
        this.message = message;
        this.url = url;
    }
}
