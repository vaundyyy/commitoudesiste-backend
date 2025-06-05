package com.just.commitoudesiste.commitoudesiste_backend.service;

import com.just.commitoudesiste.commitoudesiste_backend.model.MotivoDenuncia;
import com.just.commitoudesiste.commitoudesiste_backend.repository.MotivoDenunciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotivoDenunciaService {

    private final MotivoDenunciaRepository motivoRepository;

    public MotivoDenunciaService(MotivoDenunciaRepository motivoRepository) {
        this.motivoRepository = motivoRepository;
    }

    public List<MotivoDenuncia> listarTodos() {
        return motivoRepository.findAll();
    }

    public Optional<MotivoDenuncia> buscarPorId(Long id) {
        return motivoRepository.findById(id);
    }

    public MotivoDenuncia salvar(MotivoDenuncia motivo) {
        return motivoRepository.save(motivo);
    }

    public void deletar(Long id) {
        motivoRepository.deleteById(id);
    }
}
