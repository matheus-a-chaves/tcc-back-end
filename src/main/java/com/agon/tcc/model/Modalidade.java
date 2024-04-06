package com.agon.tcc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Modalidade {
	
	FUTEBOL(1),
	FUTSAL(2),
	FUT7(3),
	BASQUETE(4),
	VOLEI(5),
	HANDEBOL(6);

	private final Integer codigoModalidade;
	public static Modalidade getByCode(final Integer codigo) {
	    return Arrays.stream(Modalidade.values()).filter(
				modalidade -> modalidade.getCodigoModalidade()
						.equals(codigo))
				.findFirst().orElseThrow(() -> new RuntimeException("Código da modalidade inválido."));
	}

	public static Modalidade getByName(final String situacao) {
	return Arrays.stream(Modalidade.values()).filter(
			modalidade -> modalidade.name()
					.equalsIgnoreCase(situacao))
			.findFirst().orElseThrow(() -> new RuntimeException("Nome da modalidade inválido."));
	}

}
