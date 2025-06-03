package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.AlertaSuspeito;
import com.just.commitoudesiste.commitoudesiste_backend.repository.AlertaSuspeitoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private static final Logger logger = LoggerFactory.getLogger(AlertaController.class);
    private final AlertaSuspeitoRepository alertaRepo;

    public AlertaController(AlertaSuspeitoRepository alertaRepo) {
        this.alertaRepo = alertaRepo;
    }

    @GetMapping("/todos")
    public ResponseEntity<?> listarAlertas() {
        try {
            logger.info("Buscando todos os alertas suspeitos...");
            List<AlertaSuspeito> alertas = alertaRepo.findAll();
            if (alertas.isEmpty()) {
                logger.warn("Nenhum alerta encontrado.");
                return ResponseEntity.ok("Nenhum alerta encontrado.");
            }
            logger.info("Alertas encontrados: {}", alertas.size());
            return ResponseEntity.ok(alertas);
        } catch (Exception e) {
            logger.error("Erro ao buscar alertas: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro ao buscar alertas: " + e.getMessage());
        }
    }
}