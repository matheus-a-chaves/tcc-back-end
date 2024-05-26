package com.agon.tcc.model;

import com.agon.tcc.dto.EquipeGrupoDTO;

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
@Table(name = "equipe_grupo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EquipeGrupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "equipe_id")
	private Equipe equipe;

	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer pontos;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer qtdJogos;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer vitorias;
    
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer empates;
    
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer derrotas;

	@Column(name = "saldo_gols")
    private String saldoGols;
		
	public EquipeGrupo(EquipeGrupoDTO equipeGrupoDTO) {
		this.id = equipeGrupoDTO.id();
		this.equipe = equipeGrupoDTO.equipe();
		this.grupo = equipeGrupoDTO.grupo();
		this.pontos = equipeGrupoDTO.pontos();
		this.qtdJogos = equipeGrupoDTO.qtdJogos();
		this.vitorias = equipeGrupoDTO.vitorias();
		this.empates = equipeGrupoDTO.empates();
		this.derrotas = equipeGrupoDTO.derrotas();
		this.saldoGols = equipeGrupoDTO.saldoGols();
	}
}
