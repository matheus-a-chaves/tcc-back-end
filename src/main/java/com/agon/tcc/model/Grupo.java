package com.agon.tcc.model;

import java.util.List;

import com.agon.tcc.dto.GrupoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grupo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true)
	private String nome;
	
	@Column(nullable = true)
    private Integer totalJogos;
	
	@ManyToOne
	@JoinColumn(name = "campeonato_id")
	private Campeonato campeonato;
	
	@OneToMany(mappedBy = "grupo")
	private List<EquipeGrupo> equipesGrupos;
	
	@OneToMany(mappedBy = "grupo")
	private List<Partida> partidas;
	
	public Grupo(GrupoDTO grupoDTO) {
		this.nome = grupoDTO.nome();
		this.totalJogos = grupoDTO.totalJogos();
		this.campeonato = grupoDTO.campeonato();
		this.equipesGrupos = grupoDTO.equipesGrupos();
		this.partidas = grupoDTO.partidas();
	}

}
