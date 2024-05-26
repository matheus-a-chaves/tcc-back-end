package com.agon.tcc.model;

import java.time.LocalDate;
import java.util.List;

import com.agon.tcc.dto.PartidaDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
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
@Table(name = "partida")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Partida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dataPartida;
	
	@Embedded
	private Endereco endereco;
	
	@ManyToOne
    @JoinColumn(name = "etapa_campeonato_id")
    private EtapaCampeonato etapaCampeonato;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "grupo_id", nullable = true)
    private Grupo grupo;
    
    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
    @JsonManagedReference("partida-dadosPartida")
    private List<DadosPartida> dadosPartidas;
	
	public Partida(PartidaDTO partidaDTO) {
		this.id = partidaDTO.id();
		this.dataPartida = partidaDTO.dataPartida();
		this.endereco = partidaDTO.endereco();
		this.etapaCampeonato = partidaDTO.etapaCampeonato();
		this.grupo = partidaDTO.grupo();
		this.dadosPartidas = partidaDTO.dadosPartidas();
	}
	
}
