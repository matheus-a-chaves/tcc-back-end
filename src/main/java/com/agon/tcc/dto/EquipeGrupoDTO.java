package com.agon.tcc.dto;

import org.springframework.lang.NonNull;

import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.Grupo;

public record EquipeGrupoDTO(Long id,
						     Equipe equipe,
						     Grupo grupo,
							 @NonNull
							 Integer pontos,
							 @NonNull
							 Integer qtdJogos,
						     @NonNull
						     Integer vitorias,
						     @NonNull
						     Integer empates,
						     @NonNull
						     Integer derrotas,
						     String saldoGols) {

}
