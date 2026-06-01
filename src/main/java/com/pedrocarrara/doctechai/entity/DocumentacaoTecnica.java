package com.pedrocarrara.doctechai.entity;

import com.pedrocarrara.doctechai.enums.Linguagem;
import com.pedrocarrara.doctechai.enums.TipoCodigo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "documentacoes_tecnicas")
public class DocumentacaoTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCodigo tipoCodigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Linguagem linguagem;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String codigoFonte;


    @Column(columnDefinition = "TEXT")
    private String documentacaoGerada;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoCodigo getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(TipoCodigo tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    public Linguagem getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(Linguagem linguagem) {
        this.linguagem = linguagem;
    }

    public String getCodigoFonte() {
        return codigoFonte;
    }

    public void setCodigoFonte(String codigoFonte) {
        this.codigoFonte = codigoFonte;
    }

    public String getDocumentacaoGerada() {
        return documentacaoGerada;
    }

    public void setDocumentacaoGerada(String documentacaoGerada) {
        this.documentacaoGerada = documentacaoGerada;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public DocumentacaoTecnica() {
    }

    public DocumentacaoTecnica(String titulo, TipoCodigo tipoCodigo, Linguagem linguagem, String codigoFonte, String documentacaoGerada) {
        this.titulo = titulo;
        this.tipoCodigo = tipoCodigo;
        this.linguagem = linguagem;
        this.codigoFonte = codigoFonte;
        this.documentacaoGerada = documentacaoGerada;
    }
    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
