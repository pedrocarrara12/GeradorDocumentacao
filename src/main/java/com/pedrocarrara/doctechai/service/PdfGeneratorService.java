package com.pedrocarrara.doctechai.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.pedrocarrara.doctechai.exception.PdfGenerationException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] converterHtmlParaPdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        } catch (Exception exception) {
            throw new PdfGenerationException("Falha ao gerar PDF da documentacao.", exception);
        }
    }
}
