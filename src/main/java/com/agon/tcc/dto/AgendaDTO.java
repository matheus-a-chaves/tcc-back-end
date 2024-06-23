package com.agon.tcc.dto;

import java.time.LocalDateTime;

import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Equipe;

public record AgendaDTO( Long idAmistoso,
		                 LocalDateTime dataPartida,
						 Endereco endereco,
						 Equipe equipeCasa,
						 Equipe equipeVisitante) {
}
