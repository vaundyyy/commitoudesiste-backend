package com.just.commitoudesiste.commitoudesiste_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motivos_denuncia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotivoDenuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;
}
