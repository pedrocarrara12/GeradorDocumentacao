package com.pedrocarrara.doctechai.controller;

import com.pedrocarrara.doctechai.dto.DocumentacaoTecnicaResponse;
import com.pedrocarrara.doctechai.entity.DocumentacaoTecnica;
import com.pedrocarrara.doctechai.service.DocumentacaoTecnicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/documentacoes")
@RestController
public class DocumentacaoTecnicaController {

    private final DocumentacaoTecnicaService  documentacaoTecnicaService;

    public DocumentacaoTecnicaController(DocumentacaoTecnicaService documentacaoTecnicaService) {
        this.documentacaoTecnicaService = documentacaoTecnicaService;
    }

    @GetMapping
    public ResponseEntity<List<DocumentacaoTecnicaResponse>> getDocumentacaoTecnicas() {
        return ResponseEntity.ok(documentacaoTecnicaService.listarDocumentacaoTecnicas());

    }
}



