package com.pedrocarrara.doctechai.service;

import com.pedrocarrara.doctechai.dto.GerarDocumentacaoRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HtmlTemplateService {

    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public String montarHtml(GerarDocumentacaoRequestDTO request, LocalDateTime dataGeracao) {
        String titulo = escapar(request.titulo());
        String autor = escapar(request.autor());
        String descricaoDoSistema = formatarTextoSimples(request.descricaoDoSistema());
        String conteudoGeradoPelaIA = formatarConteudoGerado(request.conteudoGeradoPelaIA());
        String dataFormatada = dataGeracao.format(FORMATADOR_DATA);

        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8" />
                    <style>
                        @page {
                            size: A4;
                            margin: 2cm;
                            @bottom-center {
                                content: "Pagina " counter(page) " de " counter(pages);
                                font-size: 10px;
                                color: #666666;
                            }
                        }

                        body {
                            font-family: Arial, sans-serif;
                            color: #222222;
                            font-size: 12px;
                            line-height: 1.5;
                        }

                        .cabecalho {
                            border-bottom: 2px solid #1f4e79;
                            margin-bottom: 24px;
                            padding-bottom: 10px;
                        }

                        .cabecalho .sistema {
                            font-size: 12px;
                            color: #666666;
                            text-transform: uppercase;
                            letter-spacing: 0.5px;
                        }

                        h1 {
                            color: #1f4e79;
                            font-size: 24px;
                            margin: 6px 0 0;
                        }

                        h2 {
                            color: #1f4e79;
                            font-size: 16px;
                            margin-top: 24px;
                            border-bottom: 1px solid #dddddd;
                            padding-bottom: 4px;
                        }

                        .metadados {
                            background-color: #f5f7fa;
                            border: 1px solid #dddddd;
                            padding: 12px;
                            margin-bottom: 20px;
                        }

                        .metadados p {
                            margin: 4px 0;
                        }

                        .conteudo {
                            margin-top: 12px;
                        }

                        pre, code {
                            font-family: Consolas, monospace;
                            background-color: #f4f4f4;
                        }

                        pre {
                            padding: 10px;
                            border: 1px solid #dddddd;
                            white-space: pre-wrap;
                        }

                        .rodape {
                            margin-top: 32px;
                            padding-top: 8px;
                            border-top: 1px solid #dddddd;
                            font-size: 10px;
                            color: #777777;
                        }
                    </style>
                    <title>%s</title>
                </head>
                <body>
                    <div class="cabecalho">
                        <div class="sistema">Gerador de Documentacao Tecnica com IA</div>
                        <h1>%s</h1>
                    </div>

                    <div class="metadados">
                        <p><strong>Autor:</strong> %s</p>
                        <p><strong>Data de geracao:</strong> %s</p>
                    </div>

                    <h2>Descricao do sistema</h2>
                    <div>%s</div>

                    <h2>Documentacao gerada pela IA</h2>
                    <div class="conteudo">%s</div>

                    <div class="rodape">
                        Documento gerado automaticamente pelo sistema DocTech AI.
                    </div>
                </body>
                </html>
                """.formatted(titulo, titulo, autor, dataFormatada, descricaoDoSistema, conteudoGeradoPelaIA);
    }

    private String formatarConteudoGerado(String conteudo) {
        if (pareceHtml(conteudo)) {
            return conteudo;
        }

        return formatarTextoSimples(conteudo);
    }

    private String formatarTextoSimples(String texto) {
        return escapar(texto)
                .replace("\r\n", "\n")
                .replace("\n", "<br />");
    }

    private boolean pareceHtml(String conteudo) {
        return conteudo.contains("<") && conteudo.contains(">");
    }

    private String escapar(String texto) {
        return HtmlUtils.htmlEscape(texto);
    }
}
