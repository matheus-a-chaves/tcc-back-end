package com.agon.tcc.model;

import com.agon.tcc.dto.MembroDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "membros")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Membro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "id_equipe", nullable = false)
	private Long idEquipe;
	
	@Column(name = "id_atletica", nullable = false)
	private Long idAtletica;
	
	@Column(name = "id_jogador")
	private Long idJogador;
	
	public Membro(MembroDTO membroDTO) {
		this.id = membroDTO.id();
		this.idEquipe = membroDTO.idEquipe();
		this.idAtletica = membroDTO.idAtletica();
		this.idJogador = membroDTO.idJogador();
	}

}