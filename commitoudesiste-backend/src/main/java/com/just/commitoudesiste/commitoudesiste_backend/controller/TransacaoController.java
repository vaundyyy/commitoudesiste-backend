package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.Transacao;
import com.just.commitoudesiste.commitoudesiste_backend.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarTransacao(@RequestParam Long remetenteId,
                                                @RequestParam Long destinatarioId,
                                                @RequestParam double valor) {
        try {
            Transacao transacao = transacaoService.registrarTransacao(remetenteId, destinatarioId, valor);
            return ResponseEntity.ok(transacao);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar transação: " + e.getMessage());
        }
    }
}
