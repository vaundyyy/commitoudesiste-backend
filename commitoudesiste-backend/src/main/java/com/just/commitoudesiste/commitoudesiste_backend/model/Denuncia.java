package com.just.commitoudesiste.commitoudesiste_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "denuncias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transacao_id", nullable = false)
    private Transacao transacao;

    @ManyToOne
    @JoinColumn(name = "motivo_id", nullable = false)
    private MotivoDenuncia motivo;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDateTime dataOcorrencia;

    /*@ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;*/
}
