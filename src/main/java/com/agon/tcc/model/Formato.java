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
	private Long id;
	
	@Column(unique = true)
	private String nome;
	
	public Formato(FormatoDTO formatoDTO) {
		this.id = formatoDTO.id();
		this.nome = formatoDTO.nome();
	}
	
}
