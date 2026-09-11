package com.meatshopmanager.dto;

import java.math.BigDecimal;

public class CategoriaDespesaResumoDTO {

    private String nome;
    private BigDecimal valor;
    private BigDecimal variacao;

    public CategoriaDespesaResumoDTO(String nome, BigDecimal valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() { return nome; }
    public BigDecimal getValor() { return valor; }
    public BigDecimal getVariacao() { return variacao; }
    public void setVariacao(BigDecimal variacao) { this.variacao = variacao; }
}
