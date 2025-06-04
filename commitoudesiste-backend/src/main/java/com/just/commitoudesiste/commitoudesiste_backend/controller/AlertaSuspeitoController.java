package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.AlertaSuspeito;
import com.just.commitoudesiste.commitoudesiste_backend.repository.AlertaSuspeitoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
public class AlertaSuspeitoController {

    private static final Logger logger = LoggerFactory.getLogger(AlertaSuspeitoController.class);

    private final AlertaSuspeitoRepository alertaRepo;

    public AlertaSuspeitoController(AlertaSuspeitoRepository alertaRepo) {
        this.alertaRepo = alertaRepo;
    }

    @GetMapping
    public ResponseEntity<List<AlertaSuspeito>> listarAlertas() {
        logger.info("🔍 Buscando todos os alertas suspeitos");
        return ResponseEntity.ok(alertaRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<AlertaSuspeito> alerta = alertaRepo.findById(id);
        if (alerta.isPresent()) {
            logger.info("✅ Alerta encontrado: {}", alerta.get());
            return ResponseEntity.ok(alerta.get());
        } else {
            logger.warn("⚠️ Alerta com ID {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/por-transacao/{transacaoId}")
    public ResponseEntity<List<AlertaSuspeito>> buscarPorTransacao(@PathVariable Long transacaoId) {
        logger.info("🔍 Buscando alertas por transação ID {}", transacaoId);
        return ResponseEntity.ok(alertaRepo.findByTransacaoId(transacaoId));
    }
}
