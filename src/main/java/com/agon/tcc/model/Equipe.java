package com.agon.tcc.model;

import com.agon.tcc.dto.EquipeDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "equipe")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Equipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String imagem;
	private String modalidade;
	
	@ManyToOne
	private Usuario usuario;
	
	public Equipe(EquipeDTO equipeDTO) {
		this.id = equipeDTO.id();
		this.nome = equipeDTO.nome();
		this.imagem = equipeDTO.imagem();
		this.modalidade = equipeDTO.modalidade();
		this.usuario = equipeDTO.usuario();
	}
	
}
