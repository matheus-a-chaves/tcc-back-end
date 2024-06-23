package com.agon.tcc.model;


import java.util.List;

import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.util.Util;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	@Column(name= "nome", nullable = false)
	private String nome;
	
	@Column(name = "imagem")
	private byte[] imagem;
	
	@Column(name = "codigo_modalidade")
	private Long modalidade;

	@OneToMany(mappedBy = "equipe")
	private List<EquipeGrupo> equipeGrupos;
	
	@OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
	@JsonManagedReference("equipe-dadosPartida")
    private List<DadosPartida> dadosPartidas;
		
	public Equipe(EquipeDTO equipeDTO) {
		this.id = equipeDTO.id();
		this.nome = equipeDTO.nome();
		try {
			this.imagem = Util.convertToByte(equipeDTO.imagem());
		} catch (Exception e) {
			this.imagem = null;
		}
		this.modalidade	= equipeDTO.modalidade();
//		this.equipeGrupos = equipeDTO.equipeGrupos();
//		this.dadosPartidas = equipeDTO.dadosPartidas();
	}
}