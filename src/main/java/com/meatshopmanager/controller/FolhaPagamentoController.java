package com.meatshopmanager.controller;

import com.meatshopmanager.service.FolhaPagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/folha-pagamento")
public class FolhaPagamentoController {

    private final FolhaPagamentoService folhaPagamentoService;

    public FolhaPagamentoController(FolhaPagamentoService folhaPagamentoService) {
        this.folhaPagamentoService = folhaPagamentoService;
    }

    @PostMapping("/executar")
    public ResponseEntity<String> executar() {
        folhaPagamentoService.processarFolhaDoPagamento();
        return ResponseEntity.ok("Folha de pagamento processada com sucesso.");
    }
}
