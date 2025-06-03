package com.just.commitoudesiste.commitoudesiste_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas_suspeitos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaSuspeito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transacao_id", nullable = false)
    private Transacao transacao;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_alerta", nullable = false)
    private LocalDateTime dataAlerta;
}
