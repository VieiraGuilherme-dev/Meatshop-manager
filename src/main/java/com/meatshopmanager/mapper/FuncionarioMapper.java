package com.meatshopmanager.mapper;

import com.meatshopmanager.dto.FuncionarioRequestDTO;
import com.meatshopmanager.dto.FuncionarioResponseDTO;
import com.meatshopmanager.model.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    public Funcionario toEntity(FuncionarioRequestDTO dto) {
        return new Funcionario(
                dto.getNome(),
                dto.getCargo(),
                dto.getSalario(),
                dto.getDataAdmissao()
        );
    }

    public FuncionarioResponseDTO toResponseDTO(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCargo(),
                funcionario.getSalario(),
                funcionario.getDataAdmissao(),
                funcionario.getDataDemissao(),
                funcionario.isAtivo()
        );
    }
}
