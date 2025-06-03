package com.just.commitoudesiste.commitoudesiste_backend.repository;

import com.just.commitoudesiste.commitoudesiste_backend.model.ScoreConfianca;
import com.just.commitoudesiste.commitoudesiste_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreConfiancaRepository extends JpaRepository<ScoreConfianca, Long> {
    Optional<ScoreConfianca> findByUsuario(Usuario usuario);
}
