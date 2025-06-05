package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.MotivoDenuncia;
import com.just.commitoudesiste.commitoudesiste_backend.service.MotivoDenunciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/motivos")
public class MotivoDenunciaController {

    private final MotivoDenunciaService motivoService;

    public MotivoDenunciaController(MotivoDenunciaService motivoService) {
        this.motivoService = motivoService;
    }

    @GetMapping
    public ResponseEntity<List<MotivoDenuncia>> listarTodos() {
        List<MotivoDenuncia> motivos = motivoService.listarTodos();
        return ResponseEntity.ok(motivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotivoDenuncia> buscarPorId(@PathVariable Long id) {
        Optional<MotivoDenuncia> motivo = motivoService.buscarPorId(id);
        return motivo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MotivoDenuncia> criar(@RequestBody MotivoDenuncia motivo) {
        MotivoDenuncia salvo = motivoService.salvar(motivo);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotivoDenuncia> atualizar(@PathVariable Long id, @RequestBody MotivoDenuncia motivoAtualizado) {
        Optional<MotivoDenuncia> motivoOpt = motivoService.buscarPorId(id);
        if (motivoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MotivoDenuncia motivo = motivoOpt.get();
        motivo.setDescricao(motivoAtualizado.getDescricao());
        MotivoDenuncia salvo = motivoService.salvar(motivo);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (motivoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        motivoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
