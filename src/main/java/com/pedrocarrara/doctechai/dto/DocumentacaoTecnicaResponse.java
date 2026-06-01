package com.pedrocarrara.doctechai.dto;

import com.pedrocarrara.doctechai.entity.DocumentacaoTecnica;
import com.pedrocarrara.doctechai.enums.Linguagem;
import com.pedrocarrara.doctechai.enums.TipoCodigo;

import java.time.LocalDateTime;

public record DocumentacaoTecnicaResponse(
        Long id,
        String titulo,
        TipoCodigo tipoCodigo,
        Linguagem linguagem,
        String codigoFonte,
        String documentacaoGerada,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
    public DocumentacaoTecnicaResponse (DocumentacaoTecnica documentacaoTecnica) {
        this(documentacaoTecnica.getId(),
                documentacaoTecnica.getTitulo(),
                documentacaoTecnica.getTipoCodigo(),
                documentacaoTecnica.getLinguagem(),
                documentacaoTecnica.getCodigoFonte(),
                documentacaoTecnica.getDocumentacaoGerada(),
                documentacaoTecnica.getDataCriacao(),
                documentacaoTecnica.getDataAtualizacao());
    }

}
