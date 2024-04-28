package com.agon.tcc.model;

import java.util.List;

import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	
	private String nome;
	private byte[] imagem;
	
	@ManyToOne
    @JoinColumn(name = "codigo_modalidade")
    private Modalidade modalidade;
	
	@OneToMany(mappedBy = "equipe")
	@JsonIgnore
    private List<Usuario> usuarios;
	
	@ManyToMany(mappedBy = "equipes")
	@JsonIgnore
    private List<Partida> partidas;
	
	public Equipe(EquipeDTO equipeDTO) {
		this.id = equipeDTO.id();
		this.nome = equipeDTO.nome();
		try {
			this.imagem = Util.convertToByte(equipeDTO.imagem());
		} catch (Exception e) {
			this.imagem = null;
		}
		this.modalidade = equipeDTO.modalidade();
		this.usuarios = equipeDTO.usuarios();
	}
}