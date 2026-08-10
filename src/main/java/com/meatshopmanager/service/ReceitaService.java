package com.meatshopmanager.service;

import com.meatshopmanager.dto.ReceitaRequestDTO;
import com.meatshopmanager.dto.ReceitaResponseDTO;
import com.meatshopmanager.exception.ResourceNotFoundException;
import com.meatshopmanager.mapper.ReceitaMapper;
import com.meatshopmanager.model.Receita;
import com.meatshopmanager.repository.ReceitaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ReceitaMapper receitaMapper;

    public ReceitaService(ReceitaRepository receitaRepository, ReceitaMapper receitaMapper) {
        this.receitaRepository = receitaRepository;
        this.receitaMapper = receitaMapper;
    }

    public ReceitaResponseDTO criar(ReceitaRequestDTO dto){
        Receita receita = receitaMapper.toEntity(dto);
        Receita salva = receitaRepository.save(receita);
        return receitaMapper.toResponseDTO(salva);
    }

    public List<ReceitaResponseDTO> listarTodas() {
        return receitaRepository.findAll()
                .stream()
                .map(receitaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<ReceitaResponseDTO> listarComFiltros(Long categoriaId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        return receitaRepository.findComFiltros(categoriaId, dataInicio, dataFim, pageable)
                .map(receitaMapper::toResponseDTO);
    }

    public ReceitaResponseDTO buscarPorId(Long id){
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada com id: " + id));
        return receitaMapper.toResponseDTO(receita);
    }

    public ReceitaResponseDTO atualizar(Long id, ReceitaRequestDTO dto){
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada com id: " + id));
        Receita atualizada = receitaMapper.toEntity(dto);
        atualizada.setId(receita.getId());

        Receita salva = receitaRepository.save(atualizada);
        return receitaMapper.toResponseDTO(salva);
    }

    public void deletar(Long id){
        receitaRepository.deleteById(id);
    }
}
