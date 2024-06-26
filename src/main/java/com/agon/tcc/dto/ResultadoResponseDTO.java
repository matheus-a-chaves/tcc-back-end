package com.agon.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultadoResponseDTO {
	
	private Long id;
	private String nomeEquipe;
	private Integer vitorias;
	private Integer empates;
	private Integer derrotas;
	private String saldoGols;
	private Integer pontos;
	private Integer rodada;
	private Integer qtdJogos;
	
}
