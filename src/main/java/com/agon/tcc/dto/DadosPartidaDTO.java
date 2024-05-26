package com.agon.tcc.dto;

public record DadosPartidaDTO(Long id,
							  Integer placar,
							  Integer qtdeCartaoVermelho,
							  Integer qtdeCartaoAmarelo,
							  Integer penaltis,
							  Long equipeId,
							  Long partidaId) {

}
