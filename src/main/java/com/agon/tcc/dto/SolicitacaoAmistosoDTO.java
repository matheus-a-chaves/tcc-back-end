package com.agon.tcc.dto;

import java.time.LocalDateTime;

import com.agon.tcc.model.Amistoso;
import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.enums.StatusSolicitacao;

public record SolicitacaoAmistosoDTO(Long id, 
									 LocalDateTime dataSolicitacao,
									 Amistoso amistoso,
									 Equipe equipeVisitante,
									 StatusSolicitacao status,
									 Endereco endereco) {

}
