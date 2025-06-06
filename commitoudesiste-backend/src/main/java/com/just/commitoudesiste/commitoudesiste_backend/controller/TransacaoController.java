package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.Transacao;
import com.just.commitoudesiste.commitoudesiste_backend.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/destinatario/{id}")
    public ResponseEntity<List<Transacao>> buscarPorDestinatario(@PathVariable Long id) {
        logger.info("🔍 Buscando transações para o destinatário com ID: {}", id);
        List<Transacao> transacoes = transacaoService.buscarPorDestinatario(id);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/remetente/{id}")
    public ResponseEntity<List<Transacao>> buscarPorRemetente(@PathVariable Long id) {
        logger.info("🔍 Buscando transações para o remetente com ID: {}", id);
        List<Transacao> transacoes = transacaoService.buscarPorRemetente(id);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Transacao>> buscarPorPeriodo(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim) {
        logger.info("🔍 Buscando transações no período de {} a {}", inicio, fim);
        List<Transacao> transacoes = transacaoService.buscarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(transacoes);
    }
}