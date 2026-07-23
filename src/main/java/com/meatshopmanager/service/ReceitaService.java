package com.meatshopmanager.service;

import com.meatshopmanager.dto.ReceitaRequestDTO;
import com.meatshopmanager.dto.ReceitaResponseDTO;
import com.meatshopmanager.mapper.ReceitaMapper;
import com.meatshopmanager.model.Receita;
import com.meatshopmanager.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

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

    public ReceitaResponseDTO buscarPorId(Long id){
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        return receitaMapper.toResponseDTO(receita);
    }

    public ReceitaResponseDTO atualizar(Long id, ReceitaRequestDTO dto){
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        Receita atualizada = receitaMapper.toEntity(dto);
        atualizada.setId(receita.getId());

        Receita salva = receitaRepository.save(receita);
        return receitaMapper.toResponseDTO(salva);
    }

    public void deletar(Long id){
        receitaRepository.deleteById(id);
    }
}


