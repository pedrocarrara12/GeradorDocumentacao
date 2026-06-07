package com.pedrocarrara.doctechai.controller;

import com.pedrocarrara.doctechai.dto.GerarDocumentacaoRequestDTO;
import com.pedrocarrara.doctechai.service.DocumentacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documentacoes")
public class DocumentacaoController {

    private final DocumentacaoService documentacaoService;

    public DocumentacaoController(DocumentacaoService documentacaoService) {
        this.documentacaoService = documentacaoService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> gerarPdf(@RequestBody @Valid GerarDocumentacaoRequestDTO request) {
        byte[] pdf = documentacaoService.gerarPdf(request);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documentacao.pdf")
                .body(pdf);
    }
}
