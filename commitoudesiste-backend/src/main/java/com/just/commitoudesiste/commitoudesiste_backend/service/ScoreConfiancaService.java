package com.just.commitoudesiste.commitoudesiste_backend.service;

import com.just.commitoudesiste.commitoudesiste_backend.model.ScoreConfianca;
import com.just.commitoudesiste.commitoudesiste_backend.model.Usuario;
import com.just.commitoudesiste.commitoudesiste_backend.repository.ScoreConfiancaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ScoreConfiancaService {

    private final ScoreConfiancaRepository scoreRepo;

    public ScoreConfiancaService(ScoreConfiancaRepository scoreRepo) {
        this.scoreRepo = scoreRepo;
    }

    public ScoreConfianca atualizarScore(Usuario usuario, int novoScore) {
        ScoreConfianca score = scoreRepo.findByUsuario(usuario)
                .orElse(new ScoreConfianca());

        score.setUsuario(usuario);
        score.setScore(novoScore);
        score.setUltimaAtualizacao(LocalDateTime.now());

        return scoreRepo.save(score);
    }

    public Optional<ScoreConfianca> buscarPorUsuario(Usuario usuario) {
        return scoreRepo.findByUsuario(usuario);
    }
}