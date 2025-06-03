package com.just.commitoudesiste.commitoudesiste_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "denuncias")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDateTime dataOcorrencia;
}