package com.just.commitoudesiste.commitoudesiste_backend.service;

import com.just.commitoudesiste.commitoudesiste_backend.model.AlertaSuspeito;
import com.just.commitoudesiste.commitoudesiste_backend.model.Transacao;
import com.just.commitoudesiste.commitoudesiste_backend.repository.AlertaSuspeitoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertaService {

    private static final Logger logger = LoggerFactory.getLogger(AlertaService.class);

    private final AlertaSuspeitoRepository alertaRepo;

    public AlertaService(AlertaSuspeitoRepository alertaRepo) {
        this.alertaRepo = alertaRepo;
    }

    public AlertaSuspeito gerarAlerta(Transacao transacao, String motivo) {
        if (transacao == null || motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Transação ou motivo não podem ser nulos ou vazios");
        }

        AlertaSuspeito alerta = new AlertaSuspeito();
        alerta.setTransacao(transacao);
        alerta.setDescricao(motivo.trim());
        alerta.setDataAlerta(LocalDateTime.now());

        try {
            AlertaSuspeito alertaSalvo = alertaRepo.save(alerta);
            logger.info("✅ Alerta gerado com sucesso: {}", alertaSalvo);
            return alertaSalvo;
        } catch (Exception e) {
            logger.error("❌ Erro ao salvar alerta: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar alerta", e);
        }
    }
}