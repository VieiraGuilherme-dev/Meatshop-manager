package com.meatshopmanager.controller;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.dto.LucroRealDTO;
import com.meatshopmanager.dto.TotalExpenseDTO;
import com.meatshopmanager.service.DashboardService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/total")
    public TotalExpenseDTO totalExpenses(){
        return service.getTotalExpenses();
    }

    @GetMapping("/by-category")
    public List<ExpenseByCategoryDTO> byCategory(){
        return service.getTotalByCategory();
    }

    @GetMapping("/by-month")
    public List<ExpenseByMonthDTO> byMonth(){
        return service.getTotalByMonth();
    }

    @GetMapping("/lucro")
    public LucroRealDTO lucroReal(){
        return service.getLucroReal();
    }

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
