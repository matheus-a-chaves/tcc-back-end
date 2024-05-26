package com.agon.tcc.dto;

import java.util.List;

import com.agon.tcc.model.Campeonato;
import com.agon.tcc.model.EquipeGrupo;
import com.agon.tcc.model.Partida;

public record GrupoDTO(Long id,
					   String nome,
					   Integer totalJogos,
					   Campeonato campeonato,
					   List<EquipeGrupo> equipesGrupos,
					   List<Partida> partidas) {

}
