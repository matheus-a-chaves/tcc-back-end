package com.agon.tcc.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Endereco {
	
	private String cep;
	private String rua;
	private Integer numero;
	private String bairro;
	private String cidade;
	private String estado;
	
}
