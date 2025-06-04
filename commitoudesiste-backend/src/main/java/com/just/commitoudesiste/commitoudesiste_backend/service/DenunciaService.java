package com.just.commitoudesiste.commitoudesiste_backend.service;

import com.just.commitoudesiste.commitoudesiste_backend.model.Denuncia;
import com.just.commitoudesiste.commitoudesiste_backend.model.MotivoDenuncia;
import com.just.commitoudesiste.commitoudesiste_backend.model.Transacao;
import com.just.commitoudesiste.commitoudesiste_backend.model.Usuario;
import com.just.commitoudesiste.commitoudesiste_backend.repository.DenunciaRepository;
import com.just.commitoudesiste.commitoudesiste_backend.repository.MotivoDenunciaRepository;
import com.just.commitoudesiste.commitoudesiste_backend.repository.TransacaoRepository;
import com.just.commitoudesiste.commitoudesiste_backend.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DenunciaService {

    private static final Logger logger = LoggerFactory.getLogger(DenunciaService.class);

    private final DenunciaRepository denunciaRepo;
    private final TransacaoRepository transacaoRepo;
    private final MotivoDenunciaRepository motivoRepo;
    private final UsuarioRepository usuarioRepo;

    public DenunciaService(DenunciaRepository denunciaRepo, TransacaoRepository transacaoRepo,
                           MotivoDenunciaRepository motivoRepo, UsuarioRepository usuarioRepo) {
        this.denunciaRepo = denunciaRepo;
        this.transacaoRepo = transacaoRepo;
        this.motivoRepo = motivoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public Optional<Denuncia> registrarDenuncia(Long transacaoId, Long motivoId, Long usuarioId, String observacao) {
        Optional<Transacao> transacaoOpt = transacaoRepo.findById(transacaoId);
        Optional<MotivoDenuncia> motivoOpt = motivoRepo.findById(motivoId);
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(usuarioId);

        if (transacaoOpt.isEmpty() || motivoOpt.isEmpty() || usuarioOpt.isEmpty()) {
            logger.warn("Entidade(s) não encontrada(s): Transacao={}, Motivo={}, Usuario={}",
                    transacaoId, motivoId, usuarioId);
            return Optional.empty();
        }

        Denuncia denuncia = Denuncia.builder()
                .transacao(transacaoOpt.get())
                .motivo(motivoOpt.get())
                .observacao(observacao)
                .dataOcorrencia(LocalDateTime.now())
                .build();

        Denuncia salva = denunciaRepo.save(denuncia);
        logger.info("Denúncia registrada: {}", salva);
        return Optional.of(salva);
    }
}
