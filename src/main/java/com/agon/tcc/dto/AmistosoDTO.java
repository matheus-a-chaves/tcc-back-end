package com.agon.tcc.dto;

import java.time.LocalDateTime;

import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Modalidade;

public record AmistosoDTO(Long id,
						  LocalDateTime dataHora,
						  String status,
						  Modalidade modalidade,
						  Endereco endereco) {

}
