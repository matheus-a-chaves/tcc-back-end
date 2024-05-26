package com.agon.tcc.dto;

import java.time.LocalDate;
import java.util.List;

import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.EtapaCampeonato;
import com.agon.tcc.model.Grupo;

public record PartidaDTO(Long id, 
						 LocalDate dataPartida, 
						 Endereco endereco,
						 EtapaCampeonato etapaCampeonato,
						 Grupo grupo,
						 List<DadosPartida> dadosPartidas) {

}
