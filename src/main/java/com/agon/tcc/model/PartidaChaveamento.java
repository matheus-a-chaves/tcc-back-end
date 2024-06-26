package com.agon.tcc.model;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartidaChaveamento {
	
    private Long partida;
    private Equipe equipeUm;
    private Equipe equipeDois;
    private Endereco endereco;
    private LocalDateTime dataPartida;
    private boolean partidaFinalizada;
    private boolean partidaExpirada;

    @Override
	public int hashCode() {
		return Objects.hash(equipeDois, equipeUm, partida);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartidaChaveamento otherPartida = (PartidaChaveamento) o;
        return Objects.equals(partida, otherPartida.partida);
    }

}
