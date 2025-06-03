package com.just.commitoudesiste.commitoudesiste_backend.service;

import com.just.commitoudesiste.commitoudesiste_backend.model.ScoreConfianca;
import com.just.commitoudesiste.commitoudesiste_backend.model.Usuario;
import com.just.commitoudesiste.commitoudesiste_backend.repository.ScoreConfiancaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScoreConfiancaService {

    private final ScoreConfiancaRepository scoreRepo;

    public ScoreConfiancaService(ScoreConfiancaRepository scoreRepo) {
        this.scoreRepo = scoreRepo;
    }

    public ScoreConfianca atualizarScore(Usuario usuario, int novoScore) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        ScoreConfianca score = scoreRepo.findByUsuario(usuario)
                .orElse(new ScoreConfianca());

        if (score.getScore() != null && score.getScore() == novoScore) {
            return score;
        }

        score.setUsuario(usuario);
        score.setScore(Math.max(0, Math.min(100, novoScore)));
        score.setUltimaAtualizacao(LocalDateTime.now());

        System.out.printf("✅ Score de confiança de %s atualizado para %d%n",
                usuario.getNome(), score.getScore());

        return scoreRepo.save(score);
    }
}