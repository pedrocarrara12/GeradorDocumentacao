const API_DOCUMENTACOES = "/documentacoes";
const API_PDF = "/api/documentacoes/pdf";

const tiposCodigo = [
    "QUERY",
    "PROCEDURE",
    "FUNCTION",
    "TRIGGER",
    "VIEW",
    "PACKAGE",
    "CLASS",
    "API_ENDPOINT"
];

const linguagens = [
    "SQL",
    "PLSQL",
    "JAVA",
    "JAVASCRIPT",
    "TYPESCRIPT",
    "PYTHON",
    "C",
    "CPP",
    "CSHARP",
    "GO",
    "KOTLIN",
    "PHP",
    "RUBY",
    "RUST",
    "SWIFT",
    "HTML",
    "CSS",
    "SHELL",
    "XML",
    "JSON",
    "YAML",
    "OUTROS"
];

const state = {
    selecionada: null
};

document.addEventListener("DOMContentLoaded", () => {
    preencherSelect("create-tipoCodigo", tiposCodigo);
    preencherSelect("update-tipoCodigo", tiposCodigo);
    preencherSelect("create-linguagem", linguagens);
    preencherSelect("update-linguagem", linguagens);

    document.getElementById("create-form").addEventListener("submit", criarDocumentacao);
    document.getElementById("search-form").addEventListener("submit", buscarPorId);
    document.getElementById("update-form").addEventListener("submit", atualizarDocumentacao);
    document.getElementById("pdf-form").addEventListener("submit", gerarPdf);
    document.getElementById("reload-button").addEventListener("click", carregarDocumentacoes);
    document.getElementById("clear-selection-button").addEventListener("click", limparSelecao);

    carregarDocumentacoes();
});

window.addEventListener("unhandledrejection", (event) => {
    mostrarToast(event.reason?.message || "Ocorreu um erro inesperado.", "error");
});

function preencherSelect(id, opcoes) {
    const select = document.getElementById(id);
    select.innerHTML = opcoes
        .map((opcao) => `<option value="${opcao}">${opcao}</option>`)
        .join("");
}

async function criarDocumentacao(event) {
    event.preventDefault();

    const payload = {
        titulo: document.getElementById("create-titulo").value.trim(),
        tipoCodigo: document.getElementById("create-tipoCodigo").value,
        linguagem: document.getElementById("create-linguagem").value,
        codigoFonte: document.getElementById("create-codigoFonte").value.trim()
    };

    const documentacao = await requestJson(API_DOCUMENTACOES, {
        method: "POST",
        body: JSON.stringify(payload)
    });

    document.getElementById("create-form").reset();
    selecionarDocumentacao(documentacao);
    await carregarDocumentacoes();
    mostrarToast("Documentacao gerada e salva com sucesso.", "success");
}

async function carregarDocumentacoes() {
    const documentacoes = await requestJson(API_DOCUMENTACOES);
    renderizarTabela(documentacoes);
}

async function buscarPorId(event) {
    event.preventDefault();

    const id = document.getElementById("search-id").value;
    const documentacao = await requestJson(`${API_DOCUMENTACOES}/${id}`);
    selecionarDocumentacao(documentacao);
    mostrarToast("Documentacao carregada.", "success");
}

async function atualizarDocumentacao(event) {
    event.preventDefault();

    const id = document.getElementById("update-id").value;
    if (!id) {
        mostrarToast("Selecione uma documentacao antes de editar.", "error");
        return;
    }

    const payload = {
        titulo: document.getElementById("update-titulo").value.trim(),
        tipoCodigo: document.getElementById("update-tipoCodigo").value,
        linguagem: document.getElementById("update-linguagem").value,
        codigoFonte: document.getElementById("update-codigoFonte").value.trim()
    };

    const documentacao = await requestJson(`${API_DOCUMENTACOES}/${id}`, {
        method: "PUT",
        body: JSON.stringify(payload)
    });

    selecionarDocumentacao(documentacao);
    await carregarDocumentacoes();
    mostrarToast("Documentacao atualizada.", "success");
}

async function excluirDocumentacao(id) {
    const confirmar = window.confirm(`Deseja excluir a documentacao ${id}?`);
    if (!confirmar) {
        return;
    }

    await requestSemJson(`${API_DOCUMENTACOES}/${id}`, { method: "DELETE" });

    if (state.selecionada?.id === id) {
        limparSelecao();
    }

    await carregarDocumentacoes();
    mostrarToast("Documentacao excluida.", "success");
}

async function gerarPdf(event) {
    event.preventDefault();

    const payload = {
        titulo: document.getElementById("pdf-titulo").value.trim(),
        autor: document.getElementById("pdf-autor").value.trim(),
        descricaoDoSistema: document.getElementById("pdf-descricao").value.trim(),
        conteudoGeradoPelaIA: document.getElementById("pdf-conteudo").value.trim()
    };

    const response = await fetch(API_PDF, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        throw new Error(await extrairMensagemErro(response));
    }

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = "documentacao.pdf";
    document.body.appendChild(link);
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);

    mostrarToast("PDF gerado com sucesso.", "success");
}

function renderizarTabela(documentacoes) {
    const tbody = document.getElementById("documentacoes-body");

    if (!documentacoes.length) {
        tbody.innerHTML = `<tr><td colspan="6">Nenhuma documentacao cadastrada.</td></tr>`;
        return;
    }

    tbody.innerHTML = documentacoes.map((documentacao) => `
        <tr>
            <td>${documentacao.id}</td>
            <td>${escaparHtml(documentacao.titulo)}</td>
            <td>${documentacao.tipoCodigo}</td>
            <td>${documentacao.linguagem}</td>
            <td>${formatarData(documentacao.dataAtualizacao)}</td>
            <td>
                <div class="row-actions">
                    <button type="button" onclick="carregarDetalhe(${documentacao.id})">Ver</button>
                    <button type="button" class="secondary" onclick="prepararPdfPorId(${documentacao.id})">PDF</button>
                    <button type="button" class="danger" onclick="excluirDocumentacao(${documentacao.id})">Excluir</button>
                </div>
            </td>
        </tr>
    `).join("");
}

async function carregarDetalhe(id) {
    const documentacao = await requestJson(`${API_DOCUMENTACOES}/${id}`);
    selecionarDocumentacao(documentacao);
}

async function prepararPdfPorId(id) {
    const documentacao = await requestJson(`${API_DOCUMENTACOES}/${id}`);
    selecionarDocumentacao(documentacao);
    preencherFormularioPdf(documentacao);
    mostrarToast("Formulario de PDF preenchido.", "success");
}

function selecionarDocumentacao(documentacao) {
    state.selecionada = documentacao;

    document.getElementById("update-id").value = documentacao.id;
    document.getElementById("update-titulo").value = documentacao.titulo ?? "";
    document.getElementById("update-tipoCodigo").value = documentacao.tipoCodigo;
    document.getElementById("update-linguagem").value = documentacao.linguagem;
    document.getElementById("update-codigoFonte").value = documentacao.codigoFonte ?? "";

    preencherFormularioPdf(documentacao);
    renderizarDetalhes(documentacao);
}

function preencherFormularioPdf(documentacao) {
    document.getElementById("pdf-titulo").value = documentacao.titulo ?? "";
    document.getElementById("pdf-descricao").value = `Documentacao tecnica gerada para codigo ${documentacao.tipoCodigo} em ${documentacao.linguagem}.`;
    document.getElementById("pdf-conteudo").value = documentacao.documentacaoGerada || documentacao.codigoFonte || "";
}

function limparSelecao() {
    state.selecionada = null;
    document.getElementById("update-form").reset();
    document.getElementById("pdf-form").reset();
    document.getElementById("details").innerHTML = "<p>Selecione uma documentacao para visualizar os detalhes.</p>";
}

function renderizarDetalhes(documentacao) {
    document.getElementById("details").innerHTML = `
        <div class="details-grid">
            <div><strong>ID</strong>${documentacao.id}</div>
            <div><strong>Tipo</strong>${documentacao.tipoCodigo}</div>
            <div><strong>Linguagem</strong>${documentacao.linguagem}</div>
            <div><strong>Criacao</strong>${formatarData(documentacao.dataCriacao)}</div>
        </div>
        <h3>${escaparHtml(documentacao.titulo)}</h3>
        <h4>Codigo-fonte</h4>
        <pre>${escaparHtml(documentacao.codigoFonte || "")}</pre>
        <h4>Documentacao gerada</h4>
        <pre>${escaparHtml(documentacao.documentacaoGerada || "Ainda nao gerada.")}</pre>
    `;
}

async function requestJson(url, options = {}) {
    const response = await fetch(url, {
        headers: { "Content-Type": "application/json" },
        ...options
    });

    if (!response.ok) {
        throw new Error(await extrairMensagemErro(response));
    }

    return response.json();
}

async function requestSemJson(url, options = {}) {
    const response = await fetch(url, {
        headers: { "Content-Type": "application/json" },
        ...options
    });

    if (!response.ok) {
        throw new Error(await extrairMensagemErro(response));
    }
}

async function extrairMensagemErro(response) {
    try {
        const erro = await response.json();
        if (erro.errors?.length) {
            return erro.errors.map((item) => `${item.campo}: ${item.mensagem}`).join("\n");
        }
        return `Erro HTTP ${response.status}`;
    } catch {
        return `Erro HTTP ${response.status}`;
    }
}

function mostrarToast(mensagem, tipo = "") {
    const toast = document.getElementById("toast");
    toast.textContent = mensagem;
    toast.className = `toast show ${tipo}`;

    window.setTimeout(() => {
        toast.className = "toast";
    }, 3500);
}

function formatarData(data) {
    if (!data) {
        return "-";
    }
    return new Date(data).toLocaleString("pt-BR");
}

function escaparHtml(valor) {
    return String(valor)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}
