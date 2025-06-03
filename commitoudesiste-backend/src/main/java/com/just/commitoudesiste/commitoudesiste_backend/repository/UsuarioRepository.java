package com.just.commitoudesiste.commitoudesiste_backend.repository;

import com.just.commitoudesiste.commitoudesiste_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpfCnpj(String cpfCnpj);
}
