package com.meatshopmanager.service;

import com.meatshopmanager.dto.ReceitaRequestDTO;
import com.meatshopmanager.dto.ReceitaResponseDTO;
import com.meatshopmanager.mapper.ReceitaMapper;
import com.meatshopmanager.model.Categoria;
import com.meatshopmanager.model.Receita;
import com.meatshopmanager.model.TipoCategoria;
import com.meatshopmanager.repository.ReceitaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceitaServiceTest {

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private ReceitaMapper receitaMapper;

    @InjectMocks
    private ReceitaService receitaService;

    @Test
    void deveCriarReceitaComSucesso() {
   
        ReceitaRequestDTO requestDTO = new ReceitaRequestDTO();
        requestDTO.setDescricao("Venda de carne");
        requestDTO.setValor(new BigDecimal("500.00"));
        requestDTO.setData(LocalDate.of(2026, 7, 22));
        requestDTO.setCategoriaId(80L);

        Categoria categoria = new Categoria("Vendas", TipoCategoria.RECEITA, null);
        categoria.setId(80L);

        Receita receita = new Receita("Venda de carne", new BigDecimal("500.00"), LocalDate.of(2026, 7, 22), categoria);
        Receita receitaSalva = new Receita("Venda de carne", new BigDecimal("500.00"), LocalDate.of(2026, 7, 22), categoria);
        receitaSalva.setId(1L);

        ReceitaResponseDTO responseDTO = new ReceitaResponseDTO(
                1L, "Venda de carne", new BigDecimal("500.00"), LocalDate.of(2026, 7, 22), 80L, "Vendas");

        when(receitaMapper.toEntity(requestDTO)).thenReturn(receita);
        when(receitaRepository.save(receita)).thenReturn(receitaSalva);
        when(receitaMapper.toResponseDTO(receitaSalva)).thenReturn(responseDTO);

        ReceitaResponseDTO resultado = receitaService.criar(requestDTO);

        assertEquals("Venda de carne", resultado.getDescricao());
        assertEquals(1L, resultado.getId());
    }
}