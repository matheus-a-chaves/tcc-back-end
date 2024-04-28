package com.agon.tcc.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import com.agon.tcc.dto.CampeonatoDTO;
import com.agon.tcc.util.Util;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	@JoinColumn(name = "codigo_formato")
	private Formato formato;
	
	@ManyToOne
	@JoinColumn(name = "codigo_modalidade")
	private Modalidade modalidade;
	
	@ManyToMany
    @JoinTable(
        name = "campeonato_usuario",
        joinColumns = @JoinColumn(name = "campeonato_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios;
	
	@ManyToMany
    @JoinTable(
        name = "campeonato_partida",
        joinColumns = @JoinColumn(name = "campeonato_id"),
        inverseJoinColumns = @JoinColumn(name = "partida_id")
    )
    private List<Partida> partidas;
	
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
		try {
			this.regulamento = Util.convertToByte(campeonatoDTO.regulamento());
		} catch (Exception e) {
			this.regulamento = null;
		}
		try {
			this.imagemCampeonato = Util.convertToByte(campeonatoDTO.imagemCampeonato());
		} catch (Exception e) {
			this.imagemCampeonato = null;
		}
		this.formato = campeonatoDTO.formato();
		this.modalidade = campeonatoDTO.modalidade();
	}

}
