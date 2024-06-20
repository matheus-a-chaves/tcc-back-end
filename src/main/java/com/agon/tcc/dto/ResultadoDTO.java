package com.agon.tcc.dto;

public record ResultadoDTO(Long id,
							Integer vitorias,
							   Integer empates,
							   Integer derrotas,
							   String saldoGols,
							   Integer pontos,
							   String rodada) {

}
