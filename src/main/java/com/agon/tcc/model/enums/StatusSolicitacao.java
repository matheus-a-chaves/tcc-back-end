package com.agon.tcc.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusSolicitacao {
	
    PENDENTE(1), 
    CONFIRMADO(2), 
    RECUSADO(3);
	
	private int codigoStatus;
	
	public static StatusSolicitacao fromCodigo(int codigoStatus) {
		for (StatusSolicitacao valor : StatusSolicitacao.values()) {
			if (codigoStatus == valor.getCodigoStatus()) {
				return valor;
			}
		}
		throw new IllegalArgumentException("Status de Solicitação inválido!");
	}
	
}
