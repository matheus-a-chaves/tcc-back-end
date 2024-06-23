package com.agon.tcc.dto;

import java.time.LocalDateTime;

import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Modalidade;
import com.agon.tcc.model.enums.StatusSolicitacao;

public record AmistosoDTO(Long id,
						  LocalDateTime dataHora,
						  StatusSolicitacao statusAmistoso,
						  Modalidade modalidade,
						  Endereco endereco) {

}
