package com.agon.tcc.model;

import com.agon.tcc.dto.DadosPartidaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "dados_partida")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DadosPartida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer placar;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer qtdeCartaoVermelho;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer qtdeCartaoAmarelo;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer penaltis;
	
	@ManyToOne
	@JoinColumn(name = "partida_id")
	@JsonBackReference
	private Partida partida;
	
	@ManyToOne
	@JoinColumn(name = "equipe_id")
	@JsonBackReference
	private Equipe equipe;
	
	@Column(name = "dados_atualizados")
	private boolean dadosAtualizados;
	
	public DadosPartida(DadosPartidaDTO dadosPartidaDTO) {
		this.id = dadosPartidaDTO.id();
		this.placar = dadosPartidaDTO.placar();
		this.qtdeCartaoVermelho = dadosPartidaDTO.qtdeCartaoVermelho();
		this.qtdeCartaoAmarelo = dadosPartidaDTO.qtdeCartaoAmarelo();
		this.penaltis = dadosPartidaDTO.penaltis();
	}
	
}
