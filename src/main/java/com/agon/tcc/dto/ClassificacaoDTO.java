package com.agon.tcc.dto;

public record ClassificacaoDTO(Long id,
							   Integer vitorias,
							   Integer empates,
							   Integer derrotas,
							   String saldoGols,
							   Integer pontos,
							   String rodada) {

}
