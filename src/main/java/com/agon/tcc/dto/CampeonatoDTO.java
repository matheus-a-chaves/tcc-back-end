package com.agon.tcc.dto;

import java.time.LocalDate;

import com.agon.tcc.model.Formato;
import com.agon.tcc.model.Modalidade;

public record CampeonatoDTO(Long id, 
							String nome, 
							Integer quantidadeEquipes, 
							LocalDate dataInicio, 
							LocalDate dataFim, 
							byte[] regulamento, 
							byte[] imagemCampeonato, 
							Formato formato, 
							Modalidade modalidade) {

}
