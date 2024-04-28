package com.agon.tcc.dto;

import java.time.LocalDate;
import java.util.List;

import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Equipe;

public record PartidaDTO(Long id, 
						 LocalDate dataPartida, 
						 Endereco endereco, 
						 List<Equipe> equipes, 
						 Integer placarA,
						 Integer placarB, 
						 Long campeonatoId, 
						 Long resultadoId) {

	public PartidaDTO(Long id, LocalDate dataPartida, Endereco endereco, 
					  List<Equipe> equipes, Integer placarA, Integer placarB) {
		this(id, dataPartida, endereco, equipes, placarA, placarB, null, null);
	}
}
