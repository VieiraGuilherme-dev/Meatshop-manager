package com.meatshopmanager.service;

import com.meatshopmanager.exception.ResourceNotFoundException;
import com.meatshopmanager.model.Categoria;
import com.meatshopmanager.model.Expense;
import com.meatshopmanager.model.Funcionario;
import com.meatshopmanager.repository.CategoriaRepository;
import com.meatshopmanager.repository.ExpenseRepository;
import com.meatshopmanager.repository.FuncionarioRepository;
import com.meatshopmanager.strategy.CalculoPagamentoStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FolhaPagamentoService {

    private static final Logger log = LoggerFactory.getLogger(FolhaPagamentoService.class);
    private static final DateTimeFormatter FORMATTER_MES_ANO = DateTimeFormatter.ofPattern("MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ExpenseRepository expenseRepository;
    private final CalculoPagamentoStrategy calculoPagamentoStrategy;

    public FolhaPagamentoService(
            FuncionarioRepository funcionarioRepository,
            CategoriaRepository categoriaRepository,
            ExpenseRepository expenseRepository,
            CalculoPagamentoStrategy calculoPagamentoStrategy) {
        this.funcionarioRepository = funcionarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.expenseRepository = expenseRepository;
        this.calculoPagamentoStrategy = calculoPagamentoStrategy;
    }

    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void processarFolhaDoPagamento() {
        log.info("Iniciando processamento da folha de pagamento...");

        Categoria categoriaSalarios = categoriaRepository.findByNome("Salários")
                .orElseThrow(() -> new ResourceNotFoundException("Categoria 'Salários' não encontrada"));

        List<Funcionario> funcionariosAtivos = funcionarioRepository.findByAtivo(true);
        log.info("{} funcionário(s) ativo(s) encontrado(s)", funcionariosAtivos.size());

        LocalDate hoje = LocalDate.now();
        String mesAno = hoje.format(FORMATTER_MES_ANO);
        BigDecimal totalGerado = BigDecimal.ZERO;

        for (Funcionario funcionario : funcionariosAtivos) {
            BigDecimal valor = calculoPagamentoStrategy.calcular(funcionario);

            Expense despesa = new Expense();
            despesa.setDescription("Salário de " + funcionario.getNome() + " - " + mesAno);
            despesa.setCategoria(categoriaSalarios);
            despesa.setFuncionario(funcionario);
            despesa.setAmount(valor);
            despesa.setExpenseDate(hoje);

            expenseRepository.save(despesa);
            totalGerado = totalGerado.add(valor);
        }

        log.info("Folha de pagamento concluída — {} funcionário(s) processado(s), valor total: R$ {}",
                funcionariosAtivos.size(), totalGerado);
    }
}
