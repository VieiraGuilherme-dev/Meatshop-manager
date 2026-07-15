package com.meatshopmanager.service;

import com.meatshopmanager.dto.CategoriaRequestDTO;
import com.meatshopmanager.dto.CategoriaResponseDTO;
import com.meatshopmanager.exception.CategoriaDuplicadaException;
import com.meatshopmanager.exception.CategoriaEmUsoException;
import com.meatshopmanager.exception.ResourceNotFoundException;
import com.meatshopmanager.mapper.CategoriaMapper;
import com.meatshopmanager.model.Categoria;
import com.meatshopmanager.model.TipoCategoria;
import com.meatshopmanager.repository.CategoriaRepository;
import com.meatshopmanager.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void deveCriarCategoriaComSucesso() {
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();
        requestDTO.setNome("Água");
        requestDTO.setTipo(TipoCategoria.DESPESA);

        Categoria categoria = new Categoria("Água", TipoCategoria.DESPESA, null);
        Categoria categoriaSalva = new Categoria("Água", TipoCategoria.DESPESA, null);
        categoriaSalva.setId(1L);

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO(1L, "Água", TipoCategoria.DESPESA, null);

        when(categoriaRepository.existsByNome("Água")).thenReturn(false);
        when(categoriaMapper.toEntity(requestDTO)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoriaSalva);
        when(categoriaMapper.toResponseDTO(categoriaSalva)).thenReturn(responseDTO);

        CategoriaResponseDTO resultado = categoriaService.criar(requestDTO);

        assertEquals("Água", resultado.getNome());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoCriarComNomeDuplicado() {
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();
        requestDTO.setNome("Água");
        requestDTO.setTipo(TipoCategoria.DESPESA);

        when(categoriaRepository.existsByNome("Água")).thenReturn(true);

        assertThrows(CategoriaDuplicadaException.class, () -> categoriaService.criar(requestDTO));
    }

    @Test
    void deveLancarExcecaoAoDeletarCategoriaEmUso() {
        Long id = 1L;

        when(categoriaRepository.existsById(id)).thenReturn(true);
        when(expenseRepository.existsByCategoria_Id(id)).thenReturn(true);

        assertThrows(CategoriaEmUsoException.class, () -> categoriaService.deletar(id));
    }

    @Test
    void deveLancarExcecaoAoBuscarPorIdInexistente() {
        Long id = 99L;

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.buscarPorId(id));
    }
}
