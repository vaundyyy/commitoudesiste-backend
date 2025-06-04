package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.Denuncia;
import com.just.commitoudesiste.commitoudesiste_backend.repository.DenunciaRepository;
import com.just.commitoudesiste.commitoudesiste_backend.service.DenunciaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {

    private static final Logger logger = LoggerFactory.getLogger(DenunciaController.class);

    private final DenunciaService denunciaService;
    private final DenunciaRepository denunciaRepository;

    public DenunciaController(DenunciaService denunciaService, DenunciaRepository denunciaRepository) {
        this.denunciaService = denunciaService;
        this.denunciaRepository = denunciaRepository;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDenuncia(
            @RequestParam Long transacaoId,
            @RequestParam Long motivoId,
            @RequestParam Long usuarioId,
            @RequestParam(required = false) String observacao) {

        logger.info("Recebendo denúncia: transacaoId={}, motivoId={}, usuarioId={}", transacaoId, motivoId, usuarioId);

        try {
            Optional<Denuncia> denunciaOpt = denunciaService.registrarDenuncia(transacaoId, motivoId, usuarioId, observacao);

            if (denunciaOpt.isPresent()) {
                logger.info("Denúncia registrada com sucesso: {}", denunciaOpt.get());
                return ResponseEntity.ok(denunciaOpt.get());
            } else {
                logger.warn("Não foi possível registrar denúncia (dados podem estar inválidos)");
                return ResponseEntity.badRequest().body("Não foi possível registrar a denúncia. Verifique os dados informados.");
            }
        } catch (Exception e) {
            logger.error("Erro ao registrar denúncia: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro interno ao registrar denúncia.");
        }
    }

    @GetMapping("/transacao/{transacaoId}")
    public ResponseEntity<List<Denuncia>> buscarPorTransacao(@PathVariable Long transacaoId) {
        List<Denuncia> denuncias = denunciaRepository.findByTransacaoId(transacaoId);
        if (denuncias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(denuncias);
    }
}