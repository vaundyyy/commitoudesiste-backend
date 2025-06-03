package com.just.commitoudesiste.commitoudesiste_backend.service;

import com.just.commitoudesiste.commitoudesiste_backend.model.Denuncia;
import com.just.commitoudesiste.commitoudesiste_backend.model.MotivoDenuncia;
import com.just.commitoudesiste.commitoudesiste_backend.model.Transacao;
import com.just.commitoudesiste.commitoudesiste_backend.repository.DenunciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DenunciaService {

    private final DenunciaRepository denunciaRepo;

    public DenunciaService(DenunciaRepository denunciaRepo) {
        this.denunciaRepo = denunciaRepo;
    }

    public Denuncia registrarDenuncia(Transacao transacao, MotivoDenuncia motivo, String observacao) {
        Denuncia denuncia = new Denuncia();
        denuncia.setTransacao(transacao);
        denuncia.setMotivo(motivo);
        denuncia.setObservacao(observacao);
        denuncia.setDataOcorrencia(LocalDateTime.now());

        return denunciaRepo.save(denuncia);
    }
}