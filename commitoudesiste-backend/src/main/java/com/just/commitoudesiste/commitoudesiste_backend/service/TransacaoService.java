package com.just.commitoudesiste.commitoudesiste_backend.service;

import com.just.commitoudesiste.commitoudesiste_backend.model.*;
import com.just.commitoudesiste.commitoudesiste_backend.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);

    private final TransacaoRepository transacaoRepo;
    private final UsuarioService usuarioService;
    private final AlertaService alertaService;
    private final ScoreConfiancaService scoreService;

    public TransacaoService(TransacaoRepository transacaoRepo, UsuarioService usuarioService,
                            AlertaService alertaService, ScoreConfiancaService scoreService) {
        this.transacaoRepo = transacaoRepo;
        this.usuarioService = usuarioService;
        this.alertaService = alertaService;
        this.scoreService = scoreService;
    }

    public Transacao registrarTransacao(Long remetenteId, Long destinatarioId, double valor) throws Exception {
        Usuario remetente = usuarioService.buscarPorId(remetenteId)
                .orElseThrow(() -> new Exception("Remetente não encontrado"));

        Usuario destinatario = usuarioService.buscarPorId(destinatarioId)
                .orElseThrow(() -> new Exception("Destinatário não encontrado"));

        Transacao transacao = new Transacao();
        transacao.setRemetente(remetente);
        transacao.setDestinatario(destinatario);
        transacao.setValor(BigDecimal.valueOf(valor));
        transacao.setDataHora(LocalDateTime.now());

        Transacao transacaoSalva = transacaoRepo.save(transacao);

        aplicarRegrasAntifraude(transacaoSalva, destinatario);

        return transacaoSalva;
    }

    public List<Transacao> buscarPorDestinatario(Long destinatarioId) {
        return transacaoRepo.findByDestinatarioId(destinatarioId);
    }

    public List<Transacao> buscarPorRemetente(Long remetenteId) {
        return transacaoRepo.findByRemetenteId(remetenteId);
    }

    public List<Transacao> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return transacaoRepo.findByDataHoraBetween(inicio, fim);
    }

    private void aplicarRegrasAntifraude(Transacao transacao, Usuario destinatario) {
        ScoreConfianca scoreConfianca = destinatario.getScoreConfianca();

        if (scoreConfianca == null) {
            scoreConfianca = scoreService.atualizarScore(destinatario, 50);
        }

        int score = scoreConfianca.getScore();
        boolean isSuspeito = false;
        StringBuilder alertaMsg = new StringBuilder();

        if (score < 40) {
            isSuspeito = true;
            alertaMsg.append("Destinatário possui score de confiança baixo (").append(score).append("). ");
        }

        if (destinatario.getDataCriacao().isAfter(LocalDateTime.now().minusMonths(1))) {
            isSuspeito = true;
            alertaMsg.append("Conta do destinatário foi criada recentemente. ");
        }

        if (transacao.getValor().compareTo(BigDecimal.valueOf(1000)) > 0) {
            isSuspeito = true;
            alertaMsg.append("Transação com valor elevado sem histórico anterior. ");
        }

        if (isSuspeito) {
            AlertaSuspeito alerta = alertaService.gerarAlerta(transacao, alertaMsg.toString().trim());
            logger.info("Alerta gerado e processado: {}", alerta);
        }
    }
}