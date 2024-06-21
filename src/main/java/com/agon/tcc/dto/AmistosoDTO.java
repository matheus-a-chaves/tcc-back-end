package com.agon.tcc.dto;

import java.time.LocalDateTime;

import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Modalidade;
import com.agon.tcc.model.Partida;

public record AmistosoDTO(Long id,
						  Long idEquipeCasa,
						  Long idEquipeVisitante,
						  Modalidade modalidade,
						  Endereco endereco,
						  LocalDateTime dataHora,
						  Partida partida) {

	public AmistosoDTO(Long id, LocalDateTime dataHorario, Modalidade modalidade, Partida partida) {
        this(id, null, null, modalidade, null, dataHorario, partida);
    }

}
