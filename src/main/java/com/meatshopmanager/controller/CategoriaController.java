package com.meatshopmanager.controller;

import com.meatshopmanager.dto.CategoriaRequestDTO;
import com.meatshopmanager.dto.CategoriaResponseDTO;
import com.meatshopmanager.model.TipoCategoria;
import com.meatshopmanager.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

     private final CategoriaService categoriaService;

     public CategoriaController(CategoriaService categoriaService) {
         this.categoriaService = categoriaService;
     }

     @PostMapping
     public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
         CategoriaResponseDTO criada = categoriaService.criar(dto);
         return ResponseEntity.status(HttpStatus.CREATED).body(criada);
     }

      @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar(
              @RequestParam(required = false)TipoCategoria tipo){
         if(tipo != null) {
             return ResponseEntity.ok(categoriaService.listarPorTipo(tipo));
         }
         return ResponseEntity.ok(categoriaService.listarTodas());
      }

      @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
         return ResponseEntity.ok(categoriaService.buscarPorId(id));
      }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.ok(categoriaService.atualizar(id, dto));
    }

      @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
         categoriaService.deletar(id);
         return ResponseEntity.noContent().build();
      }
}
