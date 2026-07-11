package com.meatshopmanager.service;

import com.meatshopmanager.dto.CategoriaRequestDTO;
import com.meatshopmanager.dto.CategoriaResponseDTO;
import com.meatshopmanager.mapper.CategoriaMapper;
import com.meatshopmanager.model.Categoria;
import com.meatshopmanager.model.TipoCategoria;
import com.meatshopmanager.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        // TODO (Claude Code vai adicionar): validar nome duplicado antes de salvar
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria salva = categoriaRepository.save(categoria);
        return categoriaMapper.toResponseDTO(salva);
    }

    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CategoriaResponseDTO> listarPorTipo(TipoCategoria tipo) {
        return categoriaRepository.findByTipo(tipo)
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada")); // TODO: trocar por ResourceNotFoundException
        return categoriaMapper.toResponseDTO(categoria);
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada")); // TODO: trocar por ResourceNotFoundException

        categoria.setNome(dto.getNome());
        categoria.setTipo(dto.getTipo());
        categoria.setDescricao(dto.getDescricao());

        Categoria atualizada = categoriaRepository.save(categoria);
        return categoriaMapper.toResponseDTO(atualizada);
    }

    public void deletar(Long id) {
        // TODO (Claude Code vai adicionar): validar se categoria está em uso antes de deletar
        categoriaRepository.deleteById(id);
    }
}