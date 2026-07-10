package com.meatshopmanager.mapper;

import com.meatshopmanager.dto.CategoriaRequestDTO;
import com.meatshopmanager.dto.CategoriaResponseDTO;
import com.meatshopmanager.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequestDTO dto) {
        return new Categoria(dto.getNome(), dto.getTipo(), dto.getDescricao());
    }

    public CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getTipo(),
                categoria.getDescricao()
        );
    }
}
