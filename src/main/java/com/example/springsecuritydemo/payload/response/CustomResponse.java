package com.example.springsecuritydemo.payload.response;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import static java.time.LocalDateTime.now;

@Data
@SuperBuilder
public class CustomResponse<T> {
    private Integer code;
    private String serverTime = now().toString();
    private T data;

    public CustomResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
}
