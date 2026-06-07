package com.pedrocarrara.doctechai.service;

import com.pedrocarrara.doctechai.dto.GerarDocumentacaoRequestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentacaoService {

    private final HtmlTemplateService htmlTemplateService;
    private final PdfGeneratorService pdfGeneratorService;

    public DocumentacaoService(HtmlTemplateService htmlTemplateService, PdfGeneratorService pdfGeneratorService) {
        this.htmlTemplateService = htmlTemplateService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    public byte[] gerarPdf(GerarDocumentacaoRequestDTO request) {
        LocalDateTime dataGeracao = LocalDateTime.now();
        String html = htmlTemplateService.montarHtml(request, dataGeracao);

        return pdfGeneratorService.converterHtmlParaPdf(html);
    }
}
