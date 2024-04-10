package com.agon.tcc.model;

import java.time.DateTimeException;
import java.time.LocalDate;

import com.agon.tcc.dto.CampeonatoDTO;

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
@Table(name = "campeonato")
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
	private byte[] regulamento;
	private byte[] imagemCampeonato;
	
	@Enumerated(EnumType.ORDINAL)
	private Formato formato;
	
	@Enumerated(EnumType.ORDINAL)
	private Modalidade modalidade;
	
	public Campeonato(CampeonatoDTO campeonatoDTO) {
		this.id = campeonatoDTO.id();
		this.nome = campeonatoDTO.nome();
		this.quantidadeEquipes = campeonatoDTO.quantidadeEquipes();
		try {
			this.dataInicio = campeonatoDTO.dataInicio();
		} catch(DateTimeException dte) {
			this.dataInicio = null;
		}
		try {
			this.dataFim = campeonatoDTO.dataFim();
		} catch(DateTimeException dte) {
			this.dataFim = null;
		}
		this.regulamento = campeonatoDTO.regulamento();
		this.imagemCampeonato = campeonatoDTO.imagemCampeonato();
		this.formato = campeonatoDTO.formato();
		this.modalidade = campeonatoDTO.modalidade();
	}

}
