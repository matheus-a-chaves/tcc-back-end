package com.agon.tcc.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.Modalidade;
import com.agon.tcc.model.Partida;

public record AmistosoDTO(Long id,
						  LocalDateTime dataHorario,
						  Modalidade modalidade, 
						  Partida partida,
						  List<Equipe> equipes) {

}
