package com.meatshopmanager.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class DemissaoRequestDTO {

    @NotNull
    private LocalDate dataDemissao;

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }
}