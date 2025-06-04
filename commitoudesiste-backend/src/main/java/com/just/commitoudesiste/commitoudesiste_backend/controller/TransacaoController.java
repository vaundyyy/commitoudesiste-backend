package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.Transacao;
import com.just.commitoudesiste.commitoudesiste_backend.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoController.class);

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarTransacao(
            @RequestParam Long remetenteId,
            @RequestParam Long destinatarioId,
            @RequestParam double valor) {

        try {
            logger.info("💸 Registrando transação de {} para {} no valor de R${}", remetenteId, destinatarioId, valor);
            Transacao transacao = transacaoService.registrarTransacao(remetenteId, destinatarioId, valor);
            logger.info("✅ Transação registrada com sucesso: {}", transacao);
            return ResponseEntity.ok(transacao);
        } catch (Exception e) {
            logger.error("❌ Erro ao registrar transação: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Erro ao registrar transação: " + e.getMessage());
        }
    }
}
