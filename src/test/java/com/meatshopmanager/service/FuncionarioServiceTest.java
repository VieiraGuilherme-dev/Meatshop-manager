package com.meatshopmanager.service;

import com.meatshopmanager.dto.DemissaoRequestDTO;
import com.meatshopmanager.dto.FuncionarioRequestDTO;
import com.meatshopmanager.dto.FuncionarioResponseDTO;
import com.meatshopmanager.exception.RegraDeNegocioException;
import com.meatshopmanager.exception.ResourceNotFoundException;
import com.meatshopmanager.mapper.FuncionarioMapper;
import com.meatshopmanager.model.Funcionario;
import com.meatshopmanager.repository.FuncionarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private FuncionarioMapper funcionarioMapper;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Test
    void deveCriarFuncionarioComSucesso() {

        FuncionarioRequestDTO requestDTO = new FuncionarioRequestDTO();
        requestDTO.setNome("João Silva");
        requestDTO.setCargo("Açougueiro");
        requestDTO.setSalario(new BigDecimal("2500.00"));
        requestDTO.setDataAdmissao(LocalDate.of(2026, 1, 15));

        Funcionario funcionario = new Funcionario("João Silva", "Açougueiro", new BigDecimal("2500.00"), LocalDate.of(2026, 1, 15));
        Funcionario funcionarioSalvo = new Funcionario("João Silva", "Açougueiro", new BigDecimal("2500.00"), LocalDate.of(2026, 1, 15));
        funcionarioSalvo.setId(1L);

        FuncionarioResponseDTO responseDTO = new FuncionarioResponseDTO(
                1L, "João Silva", "Açougueiro", new BigDecimal("2500.00"),
                LocalDate.of(2026, 1, 15), null, true);

        when(funcionarioMapper.toEntity(requestDTO)).thenReturn(funcionario);
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionarioSalvo);
        when(funcionarioMapper.toResponseDTO(funcionarioSalvo)).thenReturn(responseDTO);


        FuncionarioResponseDTO resultado = funcionarioService.criar(requestDTO);


        assertEquals("João Silva", resultado.getNome());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarPorIdInexistente() {
        Long id = 99L;

        when(funcionarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> funcionarioService.buscarPorId(id));
    }

    @Test
    void deveDemitirFuncionarioComSucesso() {
        Long id = 1L;
        LocalDate dataAdmissao = LocalDate.of(2026, 1, 15);
        LocalDate dataDemissao = LocalDate.of(2026, 7, 1);

        Funcionario funcionario = new Funcionario("João Silva", "Açougueiro", new BigDecimal("2500.00"), dataAdmissao);
        funcionario.setId(id);

        FuncionarioResponseDTO responseDTO = new FuncionarioResponseDTO(
                id, "João Silva", "Açougueiro", new BigDecimal("2500.00"),
                dataAdmissao, dataDemissao, false);

        DemissaoRequestDTO dto = new DemissaoRequestDTO();
        dto.setDataDemissao(dataDemissao);

        when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionario));
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);
        when(funcionarioMapper.toResponseDTO(funcionario)).thenReturn(responseDTO);

        FuncionarioResponseDTO resultado = funcionarioService.demitir(id, dto);

        assertFalse(resultado.isAtivo());
        assertEquals(dataDemissao, resultado.getDataDemissao());
    }

    @Test
    void deveLancarExcecaoAoDemitirComDataAnteriorAAdmissao() {
        Long id = 1L;
        LocalDate dataAdmissao = LocalDate.of(2026, 6, 1);

        Funcionario funcionario = new Funcionario("João Silva", "Açougueiro", new BigDecimal("2500.00"), dataAdmissao);
        funcionario.setId(id);

        DemissaoRequestDTO dto = new DemissaoRequestDTO();
        dto.setDataDemissao(LocalDate.of(2026, 1, 1));

        when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionario));

        assertThrows(RegraDeNegocioException.class, () -> funcionarioService.demitir(id, dto));
    }

    @Test
    void deveLancarExcecaoAoDemitirFuncionarioJaInativo() {
        Long id = 1L;

        Funcionario funcionario = new Funcionario("João Silva", "Açougueiro", new BigDecimal("2500.00"), LocalDate.of(2026, 1, 15));
        funcionario.setId(id);
        funcionario.setAtivo(false);

        DemissaoRequestDTO dto = new DemissaoRequestDTO();
        dto.setDataDemissao(LocalDate.of(2026, 7, 1));

        when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionario));

        assertThrows(RegraDeNegocioException.class, () -> funcionarioService.demitir(id, dto));
    }
}