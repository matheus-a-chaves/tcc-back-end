package com.agon.tcc.model.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Amistoso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date data;

    private Time horario;

    @Lob
    private byte[] logo;

    private String status;

    @ManyToOne
    @JoinColumn(name = "fk_equipe_desafiante", nullable = false)
    private Equipe equipeDesafiante;

    @ManyToOne
    @JoinColumn(name = "fk_equipe_convidada", nullable = false)
    private Equipe equipeConvidada;

    @ManyToOne
    @JoinColumn(name = "fk_local", nullable = false)
    private Local local;

    @ManyToOne
    @JoinColumn(name = "fk_modalidade", nullable = false)
    private Modalidade modalidade;

}
