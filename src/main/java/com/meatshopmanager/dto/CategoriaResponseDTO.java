package com.meatshopmanager.dto;

import com.meatshopmanager.model.TipoCategoria;

public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private TipoCategoria tipo;
    private String descricao;

    public CategoriaResponseDTO() {
    }

    public CategoriaResponseDTO(Long id, String nome, TipoCategoria tipo, String descricao) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}