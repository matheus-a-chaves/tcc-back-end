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
public class Campeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private java.sql.Date data;

    private java.sql.Time horario;

    @Lob
    private byte[] logo;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "fk_modalidade", nullable = false)
    private Modalidade modalidade;

    @ManyToOne
    @JoinColumn(name = "fk_local", nullable = false)
    private Local local;

    @ManyToOne
    @JoinColumn(name = "fk_equipe", nullable = false)
    private Equipe equipe;

}
