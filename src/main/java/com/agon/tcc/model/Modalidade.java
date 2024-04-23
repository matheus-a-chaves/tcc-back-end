package com.agon.tcc.model;

import com.agon.tcc.dto.ModalidadeDTO;

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
@Table(name = "modalidade")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Modalidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoModalidade;
	
	@Column(unique = true)
	private String descricaoModalidade;
	
	public Modalidade(ModalidadeDTO modalidadeDTO) {
		this.codigoModalidade = modalidadeDTO.codigoModalidade();
		this.descricaoModalidade = modalidadeDTO.descricaoModalidade();
	}

}
