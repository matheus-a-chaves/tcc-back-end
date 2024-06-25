package com.agon.tcc.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.agon.tcc.model.Amistoso;
import com.agon.tcc.model.Campeonato;
import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.EtapaCampeonato;
import com.agon.tcc.model.Grupo;

public record PartidaDTO(Long id, 
						 LocalDateTime dataPartida, 
						 Endereco endereco,
						 EtapaCampeonato etapaCampeonato,
						 Grupo grupo,
						 List<DadosPartida> dadosPartidas,
						 Amistoso amistoso,
						 Campeonato campeonato) {

}
