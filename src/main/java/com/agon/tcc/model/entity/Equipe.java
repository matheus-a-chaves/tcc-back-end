package com.agon.tcc.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] logo;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_modalidade", nullable = false)
    private Modalidade modalidade;

}
