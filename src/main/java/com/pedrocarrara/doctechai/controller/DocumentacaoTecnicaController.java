package com.pedrocarrara.doctechai.controller;

import com.pedrocarrara.doctechai.dto.DocumentacaoTecnicaCreateRequest;
import com.pedrocarrara.doctechai.dto.DocumentacaoTecnicaResponse;
import com.pedrocarrara.doctechai.dto.DocumentacaoTecnicaUpdateRequest;
import com.pedrocarrara.doctechai.service.DocumentacaoTecnicaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<DocumentacaoTecnicaResponse> criarDocumentacaoTecnica(
            @RequestBody @Valid DocumentacaoTecnicaCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(documentacaoTecnicaService.criarDocumentacaoTecnica(request));
    }

    @GetMapping
    public ResponseEntity<List<DocumentacaoTecnicaResponse>> getDocumentacaoTecnicas() {
        return ResponseEntity.ok(documentacaoTecnicaService.listarDocumentacaoTecnicas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<DocumentacaoTecnicaResponse> buscarDocumentacaoTecnicaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(documentacaoTecnicaService.buscarDocumentacaoTecnicaPorId(id));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDocumentacaoTecnicaPorId(@PathVariable Long id) {
        documentacaoTecnicaService.excluirDocumentacaoTecnica(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<DocumentacaoTecnicaResponse> atualizarDocumentacaoTecnica(@PathVariable Long id ,@RequestBody
         @Valid
         DocumentacaoTecnicaUpdateRequest request) {
        return ResponseEntity.ok(documentacaoTecnicaService.atualizar(id,request));



    }
}





