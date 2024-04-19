package com.agon.tcc.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Base64;

import com.agon.tcc.dto.CampeonatoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
	private Formato formato;
	
	@ManyToOne
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

		this.regulamento =  convertToByte(campeonatoDTO.regulamento());
		this.imagemCampeonato = convertToByte(campeonatoDTO.imagemCampeonato());
		this.formato = campeonatoDTO.formato();
		this.modalidade = campeonatoDTO.modalidade();
	}

	private byte[] convertToByte(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}
}
