package com.meatshopmanager.mapper;

import com.meatshopmanager.dto.ReceitaRequestDTO;
import com.meatshopmanager.dto.ReceitaResponseDTO;
import com.meatshopmanager.model.Categoria;
import com.meatshopmanager.model.Receita;
import com.meatshopmanager.repository.CategoriaRepository;
import org.springframework.stereotype.Component;

@Component
public class ReceitaMapper {

    private final CategoriaRepository categoriaRepository;

    public ReceitaMapper(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Receita toEntity(ReceitaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        return new Receita(dto.getDescricao(), dto.getValor(), dto.getData(), categoria);
    }

    public ReceitaResponseDTO toResponseDTO(Receita receita) {
        return new ReceitaResponseDTO(
                receita.getId(),
                receita.getDescricao(),
                receita.getValor(),
                receita.getData(),
                receita.getCategoria().getId(),
                receita.getCategoria().getNome()
        );
    }
}