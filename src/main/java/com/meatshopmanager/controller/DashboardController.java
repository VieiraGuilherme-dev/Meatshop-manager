package com.meatshopmanager.controller;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.dto.LucroRealDTO;
import com.meatshopmanager.dto.ResumoDashboardDTO;
import com.meatshopmanager.dto.TotalExpenseDTO;
import com.meatshopmanager.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
@Tag(name = "Dashboard", description = "Indicadores financeiros e operacionais")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping("/total")
    public TotalExpenseDTO totalExpenses(){
        return service.getTotalExpenses();
    }

    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping("/by-category")
    public List<ExpenseByCategoryDTO> byCategory(){
        return service.getTotalByCategory();
    }

    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping("/by-month")
    public List<ExpenseByMonthDTO> byMonth(){
        return service.getTotalByMonth();
    }

    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping("/lucro")
    public LucroRealDTO lucroReal(){
        return service.getLucroReal();
    }

    @Operation(
        summary = "Resumo do dashboard",
        description = "Retorna indicadores financeiros e operacionais do período informado. " +
                      "Sem parâmetros, usa o mês e ano correntes. " +
                      "Variações em relação ao mês anterior são null quando não há dados anteriores."
    )
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping("/resumo")
    public ResponseEntity<ResumoDashboardDTO> resumo(
            @Parameter(description = "Mês (1–12). Padrão: mês atual.")
            @RequestParam(required = false) Integer mes,
            @Parameter(description = "Ano (ex: 2025). Padrão: ano atual.")
            @RequestParam(required = false) Integer ano) {
        return ResponseEntity.ok(service.getResumoDashboard(mes, ano));
    }

    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping("/export/excel")
    public ResponseEntity<ByteArrayResource> exportarExcel(
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        byte[] arquivo = service.gerarRelatorioExcel(dataInicio, dataFim);
        ByteArrayResource resource = new ByteArrayResource(arquivo);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.xlsx")
                .body(resource);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping("/export/pdf")
    public ResponseEntity<ByteArrayResource> exportarPdf(
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        byte[] arquivo = service.gerarRelatorioPdf(dataInicio, dataFim);
        ByteArrayResource resource = new ByteArrayResource(arquivo);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                .body(resource);
    }
}
