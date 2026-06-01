package com.pedrocarrara.doctechai.service;

import com.pedrocarrara.doctechai.dto.DocumentacaoTecnicaCreateRequest;
import com.pedrocarrara.doctechai.dto.DocumentacaoTecnicaResponse;
import com.pedrocarrara.doctechai.dto.DocumentacaoTecnicaUpdateRequest;
import com.pedrocarrara.doctechai.entity.DocumentacaoTecnica;
import com.pedrocarrara.doctechai.exception.DocumentacaoNaoEncontradaException;
import com.pedrocarrara.doctechai.repository.DocumentacaoTecnicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentacaoTecnicaService {

    private final DocumentacaoTecnicaRepository documentacaoTecnicaRepository;

    public DocumentacaoTecnicaService(DocumentacaoTecnicaRepository documentacaoTecnicaRepository) {
        this.documentacaoTecnicaRepository = documentacaoTecnicaRepository;
    }

    public DocumentacaoTecnicaResponse criarDocumentacaoTecnica(
            DocumentacaoTecnicaCreateRequest documentacaoTecnicaCreateRequest) {
        DocumentacaoTecnica documentacaoTecnica = new DocumentacaoTecnica(
                documentacaoTecnicaCreateRequest.titulo(),
                documentacaoTecnicaCreateRequest.tipoCodigo(),
                documentacaoTecnicaCreateRequest.linguagem(),
                documentacaoTecnicaCreateRequest.codigoFonte(),
                null
        );
        DocumentacaoTecnica documentacaoSalva = documentacaoTecnicaRepository.save(documentacaoTecnica);
        return new DocumentacaoTecnicaResponse(documentacaoSalva);
    }

    public List<DocumentacaoTecnicaResponse> listarDocumentacaoTecnicas() {
        return documentacaoTecnicaRepository.findAll()
                .stream()
                .map(DocumentacaoTecnicaResponse::new)
                .toList();
    }

    public DocumentacaoTecnicaResponse buscarDocumentacaoTecnicaPorId(Long id) {
        return new DocumentacaoTecnicaResponse(buscarEntidadePorId(id));
    }

    public DocumentacaoTecnicaResponse atualizar(
            Long id,
            DocumentacaoTecnicaUpdateRequest request) {
        DocumentacaoTecnica documentacaoTecnica = buscarEntidadePorId(id);

        documentacaoTecnica.setTitulo(request.titulo());
        documentacaoTecnica.setTipoCodigo(request.tipoCodigo());
        documentacaoTecnica.setLinguagem(request.linguagem());
        documentacaoTecnica.setCodigoFonte(request.codigoFonte());

        DocumentacaoTecnica documentacaoAtualizada = documentacaoTecnicaRepository.save(documentacaoTecnica);
        return new DocumentacaoTecnicaResponse(documentacaoAtualizada);
    }

    public void excluirDocumentacaoTecnica(Long id) {
        DocumentacaoTecnica documentacaoTecnica = buscarEntidadePorId(id);
        documentacaoTecnicaRepository.delete(documentacaoTecnica);
    }

    private DocumentacaoTecnica buscarEntidadePorId(Long id) {
        return documentacaoTecnicaRepository.findById(id)
                .orElseThrow(() -> new DocumentacaoNaoEncontradaException(
                        "Documentacao tecnica nao encontrada com o ID: " + id
                ));
    }
}
