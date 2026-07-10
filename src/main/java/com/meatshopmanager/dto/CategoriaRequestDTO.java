package com.meatshopmanager.dto;

import com.meatshopmanager.model.TipoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoriaRequestDTO {

    @NotBlank(message = "O nome da categoria é obrigatório")
    private String nome;

    @NotNull(message = "O tipo da categoria é obrigatório")
    private TipoCategoria tipo;

    private String descricao;

    public CategoriaRequestDTO() {
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