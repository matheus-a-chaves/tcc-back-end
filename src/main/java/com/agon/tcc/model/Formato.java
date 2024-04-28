package com.agon.tcc.model;

import com.agon.tcc.dto.FormatoDTO;

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
@Table(name = "formato")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Formato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoFormato;
	
	@Column(unique = true)
	private String descricaoFormato;
	
	public Formato(FormatoDTO formatoDTO) {
		this.codigoFormato = formatoDTO.codigoFormato();
		this.descricaoFormato = formatoDTO.descricaoFormato();
	}
	
}
