package com.just.commitoudesiste.commitoudesiste_backend.repository;

import com.just.commitoudesiste.commitoudesiste_backend.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByDestinatarioId(Long destinatarioId);

    List<Transacao> findByRemetenteId(Long remetenteId);

    List<Transacao> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
