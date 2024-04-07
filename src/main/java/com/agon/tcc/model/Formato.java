package com.agon.tcc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Formato {
	
	PONTOS_CORRIDOS(1,"Pontos Corridos"),
	ELIMINATORIA_SIMPLES(2,"Eliminatória Simples"),
	ELIMINATORIA_DUPLA(3,"Eliminatória Dupla"),
	FASE_DE_GRUPOS_COM_ELIMINATORIA_DUPLA(3,"Fase de Grupos + Eliminatória Dupla"),
	FASE_DE_GRUPOS_COM_ELIMINATORIA_SIMPLES(4, "Fase de Grupos + Eliminatória Simples"),
	PONTOS_CORRIDOS_COM_ELIMINATORIA_SIMPLES(5, "Pontos Corridos + Eliminatória Simples"),
	PONTOS_CORRIDOS_COM_ELIMINATORIA_DUPLA(6, "Pontos Corridos + Eliminatória Dupla");

	private final Integer codigoFormato;

	private final String nomeFormato;
	public static Formato getByCode(final Integer codigo) {
		return Arrays.stream(Formato.values()).filter(
						formato -> formato.getCodigoFormato()
								.equals(codigo))
				.findFirst().orElseThrow(() -> new RuntimeException("Código da modalidade inválido."));
	}

	public static Formato getByName(final String nomeFormato) {
		return Arrays.stream(Formato.values()).filter(
						formato -> formato.getNomeFormato()
								.equalsIgnoreCase(nomeFormato))
				.findFirst().orElseThrow(() -> new RuntimeException("Nome da modalidade inválido."));
	}
}
