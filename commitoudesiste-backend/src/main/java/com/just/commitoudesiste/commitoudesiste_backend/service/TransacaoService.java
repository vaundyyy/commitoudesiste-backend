package com.just.commitoudesiste.commitoudesiste_backend.service;

import com.just.commitoudesiste.commitoudesiste_backend.model.*;
import com.just.commitoudesiste.commitoudesiste_backend.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransacaoService {

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
            alertaService.gerarAlerta(transacao, alertaMsg.toString().trim());
        }
    }
}