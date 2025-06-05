package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.ScoreConfianca;
import com.just.commitoudesiste.commitoudesiste_backend.model.Usuario;
import com.just.commitoudesiste.commitoudesiste_backend.service.ScoreConfiancaService;
import com.just.commitoudesiste.commitoudesiste_backend.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/score")
public class ScoreConfiancaController {

    private static final Logger logger = LoggerFactory.getLogger(ScoreConfiancaController.class);

    private final ScoreConfiancaService scoreService;
    private final UsuarioService usuarioService;

    public ScoreConfiancaController(ScoreConfiancaService scoreService, UsuarioService usuarioService) {
        this.scoreService = scoreService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<?> buscarScorePorUsuario(@RequestParam Long usuarioId) {
        return usuarioService.buscarPorId(usuarioId)
                .map(usuario -> {
                    Optional<ScoreConfianca> scoreOpt = scoreService.buscarPorUsuario(usuario);
                    if (scoreOpt.isPresent()) {
                        return ResponseEntity.ok(scoreOpt.get());
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("Usuário não encontrado"));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarScore(@RequestParam Long usuarioId, @RequestParam int novoScore) {
        if (novoScore < 0 || novoScore > 100) {
            logger.warn("Score inválido: {}", novoScore);
            return ResponseEntity.badRequest().body("O score deve estar entre 0 e 100");
        }

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            logger.warn("Usuário não encontrado: ID {}", usuarioId);
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }

        try {
            logger.info("Atualizando score do usuário: ID {}, Novo Score {}", usuarioId, novoScore);
            ScoreConfianca scoreAtualizado = scoreService.atualizarScore(usuarioOpt.get(), novoScore);
            logger.info("Score atualizado com sucesso: {}", scoreAtualizado);
            return ResponseEntity.ok(scoreAtualizado);
        } catch (Exception e) {
            logger.error("Erro ao atualizar score: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro ao atualizar score: " + e.getMessage());
        }
    }
}