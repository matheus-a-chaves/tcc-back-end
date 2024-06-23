package com.agon.tcc.dto;

import java.time.LocalDateTime;

import com.agon.tcc.model.Amistoso;
import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.enums.Status;

public record SolicitacaoAmistosoDTO(Long id, 
									 LocalDateTime dataSolicitacao,
									 Equipe equipeVisitante,
									 Equipe equipeCasa,
									 Status status,
									 Endereco endereco,
									 Amistoso amistoso) {

}
