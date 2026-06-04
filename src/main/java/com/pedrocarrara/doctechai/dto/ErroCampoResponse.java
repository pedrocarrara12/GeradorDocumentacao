package com.pedrocarrara.doctechai.dto;

public record ErroCampoResponse(
        String campo,
        String mensagem
) {
}
