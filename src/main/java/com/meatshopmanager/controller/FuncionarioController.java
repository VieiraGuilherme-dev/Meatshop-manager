package com.meatshopmanager.controller;

import com.meatshopmanager.dto.FuncionarioRequestDTO;
import com.meatshopmanager.dto.FuncionarioResponseDTO;
import com.meatshopmanager.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> criar(@Valid @RequestBody FuncionarioRequestDTO dto) {
        FuncionarioResponseDTO criado = funcionarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listar(
            @RequestParam(required = false) Boolean ativo) {
        if (Boolean.TRUE.equals(ativo)) {
            return ResponseEntity.ok(funcionarioService.listarAtivos());
        }
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody FuncionarioRequestDTO dto) {
        return ResponseEntity.ok(funcionarioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}