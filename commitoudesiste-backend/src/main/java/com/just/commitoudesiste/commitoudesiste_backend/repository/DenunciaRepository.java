package com.just.commitoudesiste.commitoudesiste_backend.repository;

import com.just.commitoudesiste.commitoudesiste_backend.model.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findByTransacaoId(Long transacaoId);
}