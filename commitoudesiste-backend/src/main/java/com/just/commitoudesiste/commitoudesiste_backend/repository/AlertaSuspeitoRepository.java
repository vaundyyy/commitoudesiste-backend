package com.just.commitoudesiste.commitoudesiste_backend.repository;

import com.just.commitoudesiste.commitoudesiste_backend.model.AlertaSuspeito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaSuspeitoRepository extends JpaRepository<AlertaSuspeito, Long> {
    List<AlertaSuspeito> findByTransacaoId(Long transacaoId);
}
