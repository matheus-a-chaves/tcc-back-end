package com.agon.tcc.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
	
    PENDENTE(1), 
    CONFIRMADO(2), 
    RECUSADO(3),
	CANCELADO(4);

	private int codigoStatus;
	
	public static Status fromCodigo(int codigoStatus) {
		for (Status valor : Status.values()) {
			if (codigoStatus == valor.getCodigoStatus()) {
				return valor;
			}
		}
		throw new IllegalArgumentException("Status de Solicitação inválido!");
	}
	
}
