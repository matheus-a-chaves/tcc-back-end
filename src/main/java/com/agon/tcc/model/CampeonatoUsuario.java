package com.agon.tcc.model;

import com.agon.tcc.dto.CampeonatoUsuarioDTO;

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
@Table(name = "campeonato_usuario")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CampeonatoUsuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "campeonato_id", nullable = false)
	private Long idCampeonato;
	
	@Column(name = "usuario_id")
	private Long idAtletica;
	
	@Column(name = "criador_id")
	private Long idCriador;
	
	public CampeonatoUsuario(Long idCampeonato, Long idAtletica) {
		this.idCampeonato = idCampeonato;
		this.idAtletica = idAtletica;
	}
	
	public CampeonatoUsuario(CampeonatoUsuarioDTO campeonatoUsuarioDTO) {
		this.id = campeonatoUsuarioDTO.id();
		this.idCampeonato = campeonatoUsuarioDTO.idCampeonato();
		this.idAtletica = campeonatoUsuarioDTO.idAtletica();
		this.idCriador = campeonatoUsuarioDTO.idCriador();
	}
	
}
