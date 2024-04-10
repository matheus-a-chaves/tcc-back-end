package com.agon.tcc.model;

import com.agon.tcc.dto.FormatoDTO;

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
@Table(name = "formato")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Formato {
	
	/*
	PONTOS_CORRIDOS(1,"Pontos Corridos"),
	ELIMINATORIA_SIMPLES(2,"Eliminatória Simples"),
	ELIMINATORIA_DUPLA(3,"Eliminatória Dupla"),
	FASE_DE_GRUPOS_COM_ELIMINATORIA_DUPLA(3,"Fase de Grupos + Eliminatória Dupla"),
	FASE_DE_GRUPOS_COM_ELIMINATORIA_SIMPLES(4, "Fase de Grupos + Eliminatória Simples"),
	PONTOS_CORRIDOS_COM_ELIMINATORIA_SIMPLES(5, "Pontos Corridos + Eliminatória Simples"),
	PONTOS_CORRIDOS_COM_ELIMINATORIA_DUPLA(6, "Pontos Corridos + Eliminatória Dupla");
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoFormato;
	
	private String descricaoFormato;
	
	public Formato(FormatoDTO formatoDTO) {
		this.codigoFormato = formatoDTO.id();
		this.descricaoFormato = formatoDTO.nome();
	}
	
}
