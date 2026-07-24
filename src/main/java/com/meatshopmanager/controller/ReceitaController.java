package com.meatshopmanager.controller;

import com.meatshopmanager.dto.ReceitaRequestDTO;
import com.meatshopmanager.dto.ReceitaResponseDTO;
import com.meatshopmanager.model.Receita;
import com.meatshopmanager.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService){
        this.receitaService = receitaService;
    }

    @PostMapping
    public ResponseEntity<ReceitaResponseDTO> criar(@Valid @RequestBody ReceitaRequestDTO dto){
        ReceitaResponseDTO criada = receitaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<ReceitaResponseDTO>> listar(){
        return ResponseEntity.ok(receitaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(receitaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceitaResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody ReceitaRequestDTO dto){
        return ResponseEntity.ok(receitaService.atualizar(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        receitaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
