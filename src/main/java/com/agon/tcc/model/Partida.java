package com.agon.tcc.model;

import java.time.LocalDateTime;
import java.util.List;

import com.agon.tcc.dto.PartidaDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
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

	@Column(nullable = true)
	private LocalDateTime dataPartida;
	
	@Embedded
	private Endereco endereco;
	
	@ManyToOne(optional = true)
    @JoinColumn(name = "etapa_campeonato_id", nullable = true)
    private EtapaCampeonato etapaCampeonato;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "grupo_id", nullable = true)
    private Grupo grupo;
    
    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
    @JsonManagedReference("partida-dadosPartida")
    private List<DadosPartida> dadosPartidas;
	
    @OneToOne(optional = true)
    @JoinColumn(name = "amistoso_id", nullable = true)
    private Amistoso amistoso;
    
    @OneToOne(optional = true)
    @JoinColumn(name = "campeonato_id", nullable = true)
    private Campeonato campeonato;
    
	public Partida(PartidaDTO partidaDTO) {
		this.id = partidaDTO.id();
		this.dataPartida = partidaDTO.dataPartida();
		this.endereco = partidaDTO.endereco();
		this.etapaCampeonato = partidaDTO.etapaCampeonato();
		this.grupo = partidaDTO.grupo();
		this.dadosPartidas = partidaDTO.dadosPartidas();
		this.amistoso = partidaDTO.amistoso();
		this.campeonato = partidaDTO.campeonato();
	}
	
}
