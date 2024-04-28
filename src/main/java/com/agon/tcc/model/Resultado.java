package com.agon.tcc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resultado")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resultado {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int golsEquipeCasa;
    private int golsEquipeVisitante;
    
    @OneToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;
    
}