package com.meatshopmanager.service;

import com.meatshopmanager.dto.FuncionarioRequestDTO;
import com.meatshopmanager.dto.FuncionarioResponseDTO;
import com.meatshopmanager.mapper.FuncionarioMapper;
import com.meatshopmanager.model.Funcionario;
import com.meatshopmanager.repository.FuncionarioRepository;
import com.meatshopmanager.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    public FuncionarioResponseDTO criar(FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioMapper.toEntity(dto);
        Funcionario salvo = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toResponseDTO(salvo);
    }

    public List<FuncionarioResponseDTO> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(funcionarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<FuncionarioResponseDTO> listarAtivos() {
        return funcionarioRepository.findByAtivo(true)
                .stream()
                .map(funcionarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public FuncionarioResponseDTO buscarPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
        return funcionarioMapper.toResponseDTO(funcionario);
    }

    public FuncionarioResponseDTO atualizar(Long id, FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setSalario(dto.getSalario());
        funcionario.setDataAdmissao(dto.getDataAdmissao());

        Funcionario atualizado = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        funcionarioRepository.deleteById(id);
    }
}