package com.pedrocarrara.doctechai.dto;

import com.pedrocarrara.doctechai.enums.Linguagem;
import com.pedrocarrara.doctechai.enums.TipoCodigo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DocumentacaoTecnicaCreateRequest(
        @NotNull @NotBlank
        @Size(max = 100) String titulo,
        @NotNull  TipoCodigo tipoCodigo,
        @NotNull  Linguagem linguagem,
        @NotNull @NotBlank String codigoFonte
) {
}
