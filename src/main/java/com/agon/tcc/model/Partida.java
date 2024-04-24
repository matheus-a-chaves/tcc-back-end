package com.agon.tcc.model;

import java.time.LocalDate;
import java.util.List;

import com.agon.tcc.dto.PartidaDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @JoinColumn(name = "campeonato_id")
    private Campeonato campeonato;
	
	@OneToOne(mappedBy = "partida", cascade = CascadeType.ALL)
    private Resultado resultado;
	
	@ManyToMany
    @JoinTable(
        name = "partida_equipe",
        joinColumns = @JoinColumn(name = "partida_id"),
        inverseJoinColumns = @JoinColumn(name = "equipe_id")
    )
    private List<Equipe> equipes;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer placarA;
    
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer placarB;
	
	public Partida(PartidaDTO partidaDTO) {
		this.id = partidaDTO.id();
		this.dataPartida = partidaDTO.dataPartida();
		this.endereco = partidaDTO.endereco();
		this.equipes = partidaDTO.equipes();
		this.placarA = partidaDTO.placarA();
		this.placarB = partidaDTO.placarB();
	}
	
}
