package com.pedrocarrara.doctechai.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(

        int status,
        LocalDateTime timestamp,
        List<ErroCampoResponse> errors
){
    public ErroResponse(int status, LocalDateTime timestamp, List<ErroCampoResponse> errors) {
        this.status = status;
        this.timestamp = timestamp;
        this.errors = errors;
    }
}

