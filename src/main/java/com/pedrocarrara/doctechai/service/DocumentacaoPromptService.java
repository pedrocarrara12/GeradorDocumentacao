package com.pedrocarrara.doctechai.service;

import com.pedrocarrara.doctechai.dto.DocumentacaoTecnicaCreateRequest;
import com.pedrocarrara.doctechai.entity.DocumentacaoTecnica;
import org.springframework.stereotype.Service;

@Service
public class DocumentacaoPromptService {

    public String criarPromptParaGeracao(DocumentacaoTecnicaCreateRequest request) {
        return criarPrompt(
                request.titulo(),
                request.tipoCodigo().name(),
                request.linguagem().name(),
                request.codigoFonte()
        );
    }

    public String criarPromptParaReprocessamento(DocumentacaoTecnica documentacaoTecnica) {
        return criarPrompt(
                documentacaoTecnica.getTitulo(),
                documentacaoTecnica.getTipoCodigo().name(),
                documentacaoTecnica.getLinguagem().name(),
                documentacaoTecnica.getCodigoFonte()
        );
    }

    private String criarPrompt(String titulo, String tipoCodigo, String linguagem, String codigoFonte) {
        return """
                Voce e um especialista em documentacao tecnica de software.

                Gere uma documentacao tecnica estruturada para o codigo abaixo.

                Dados do codigo:
                - Titulo: %s
                - Tipo do codigo: %s
                - Linguagem: %s

                A documentacao deve conter obrigatoriamente as secoes:
                1. Objetivo
                2. Resumo tecnico
                3. Parametros identificados
                4. Tabelas, views ou entidades utilizadas
                5. Regras de negocio identificadas
                6. Fluxo de execucao
                7. Possiveis riscos
                8. Sugestoes de melhoria
                9. Exemplo de uso
                10. Versao em Markdown

                Regras de resposta:
                - Responda em portugues do Brasil.
                - Use linguagem tecnica, clara e objetiva.
                - Se uma secao nao tiver informacao suficiente, escreva "Nao identificado".
                - Nao invente tabelas, parametros ou regras de negocio inexistentes.
                - Retorne apenas a documentacao gerada.

                Codigo-fonte:
                ```%s
                %s
                ```
                """.formatted(titulo, tipoCodigo, linguagem, linguagem.toLowerCase(), codigoFonte);
    }
}
