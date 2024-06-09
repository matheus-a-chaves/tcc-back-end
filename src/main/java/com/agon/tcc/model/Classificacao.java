package com.agon.tcc.model;

import com.agon.tcc.dto.ClassificacaoDTO;
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
@Table(name = "classificacao")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Classificacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer vitorias;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer empates;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer derrotas;
	
	@Column(nullable = false, columnDefinition = "VARCHAR(3) DEFAULT '0'")
	private String saldoGols;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer pontos;
	
	private String rodada;
	
	@ManyToOne
	@JoinColumn(name = "etapa_campeonato_id")
	@JsonBackReference
	private EtapaCampeonato etapaCampeonato;
	
	@ManyToOne
	@JoinColumn(name = "dados_partida_id")
	@JsonBackReference
	private DadosPartida dadosPartida;
	
	@ManyToOne
	@JoinColumn(name = "equipe_grupo_id")
	@JsonBackReference
	private EquipeGrupo equipeGrupo;
	
	public Classificacao(ClassificacaoDTO classificacaoDTO) {
		this.id = classificacaoDTO.id();
		this.vitorias = classificacaoDTO.vitorias();
		this.empates = classificacaoDTO.empates();
		this.derrotas = classificacaoDTO.derrotas();
		this.saldoGols = classificacaoDTO.saldoGols();
		this.pontos = classificacaoDTO.pontos();
		this.rodada = classificacaoDTO.rodada();
	}
	
}
