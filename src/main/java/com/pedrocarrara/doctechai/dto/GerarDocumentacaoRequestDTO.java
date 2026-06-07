package com.pedrocarrara.doctechai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GerarDocumentacaoRequestDTO(
        @NotBlank
        @Size(max = 150)
        String titulo,

        @NotBlank
        String descricaoDoSistema,

        @NotBlank
        String conteudoGeradoPelaIA,

        @NotBlank
        @Size(max = 100)
        String autor
) {
}
