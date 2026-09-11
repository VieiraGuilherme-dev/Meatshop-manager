package com.meatshopmanager.dto;

import java.math.BigDecimal;

public class ResumoDashboardDTO {

    private BigDecimal receitas;
    private BigDecimal despesas;
    private BigDecimal lucro;
    private BigDecimal margemLucro;
    private BigDecimal variacaoReceitas;
    private BigDecimal variacaoDespesas;
    private BigDecimal variacaoLucro;
    private long funcionariosAtivos;
    private BigDecimal totalFolha;
    private CategoriaDespesaResumoDTO maiorCategoriaDespesa;

    public ResumoDashboardDTO(
            BigDecimal receitas,
            BigDecimal despesas,
            BigDecimal lucro,
            BigDecimal margemLucro,
            BigDecimal variacaoReceitas,
            BigDecimal variacaoDespesas,
            BigDecimal variacaoLucro,
            long funcionariosAtivos,
            BigDecimal totalFolha,
            CategoriaDespesaResumoDTO maiorCategoriaDespesa) {
        this.receitas = receitas;
        this.despesas = despesas;
        this.lucro = lucro;
        this.margemLucro = margemLucro;
        this.variacaoReceitas = variacaoReceitas;
        this.variacaoDespesas = variacaoDespesas;
        this.variacaoLucro = variacaoLucro;
        this.funcionariosAtivos = funcionariosAtivos;
        this.totalFolha = totalFolha;
        this.maiorCategoriaDespesa = maiorCategoriaDespesa;
    }

    public BigDecimal getReceitas() { return receitas; }
    public BigDecimal getDespesas() { return despesas; }
    public BigDecimal getLucro() { return lucro; }
    public BigDecimal getMargemLucro() { return margemLucro; }
    public BigDecimal getVariacaoReceitas() { return variacaoReceitas; }
    public BigDecimal getVariacaoDespesas() { return variacaoDespesas; }
    public BigDecimal getVariacaoLucro() { return variacaoLucro; }
    public long getFuncionariosAtivos() { return funcionariosAtivos; }
    public BigDecimal getTotalFolha() { return totalFolha; }
    public CategoriaDespesaResumoDTO getMaiorCategoriaDespesa() { return maiorCategoriaDespesa; }
}
