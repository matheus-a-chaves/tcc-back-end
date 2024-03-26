package com.agon.tcc.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Campeonato")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Campeonato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private String nome;
	private Integer quantidadeEquipes;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private Byte[] regulamento;
	private Byte[] imagemCampeonato;
	
	@Enumerated(EnumType.ORDINAL)
	private Formato formato;
	
	@Enumerated(EnumType.STRING)
	private Modalidade modalidade;

}
