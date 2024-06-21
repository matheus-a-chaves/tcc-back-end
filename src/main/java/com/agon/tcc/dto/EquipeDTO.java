package com.agon.tcc.dto;

import java.util.List;

import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.model.EquipeGrupo;

public record EquipeDTO(Long id, 
						String nome, 
						String imagem,
						Long  modalidade,
						List<EquipeGrupo> equipeGrupos,
						List<DadosPartida> dadosPartidas) {

}
