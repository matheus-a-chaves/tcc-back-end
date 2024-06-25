package com.agon.tcc.model;

import com.agon.tcc.dto.EtapaCampeonatoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "etapa_campeonato")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EtapaCampeonato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nomeEtapa;
	
	@ManyToOne
	@JoinColumn(name = "campeonato_id")
	private Campeonato campeonato;
	
	@Column(name = "total_rodadas")
	private Integer totalRodadas;
	
	public EtapaCampeonato(EtapaCampeonatoDTO etapaCampeonatoDTO) {
		this.id = etapaCampeonatoDTO.id();
		this.nomeEtapa = etapaCampeonatoDTO.nomeEtapa();
		this.campeonato = etapaCampeonatoDTO.campeonato();
		this.totalRodadas = etapaCampeonatoDTO.totalRodadas();
	}
	
	public EtapaCampeonato(String nome, Campeonato campeonato, Integer totalRodadas) {
		this.nomeEtapa = nome;
		this.campeonato = campeonato;
		this.totalRodadas = totalRodadas;
	}
}
