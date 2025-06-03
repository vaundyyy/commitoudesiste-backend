package com.just.commitoudesiste.commitoudesiste_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "cpf_cnpj", unique = true, nullable = false)
    private String cpfCnpj;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ScoreConfianca scoreConfianca;

    public enum TipoUsuario {
        PESSOA_FISICA, PESSOA_JURIDICA
    }
}