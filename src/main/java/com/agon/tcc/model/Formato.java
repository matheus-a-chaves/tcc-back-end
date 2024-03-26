package com.agon.tcc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Formato")
public enum Formato {
	
	PONTOS_CORRIDOS,
	ELIMINATORIA_SIMPLES,
	ELIMINATORIA_DUPLA,
	FASE_DE_GRUPOS_COM_ELIMINATORIA_DUPLA,
	FASE_DE_GRUPOS_COM_ELIMINATORIA_SIMPLES,
	PONTOS_CORRIDOS_COM_ELIMINATORIA_SIMPLES,
	PONTOS_CORRIDOS_COM_ELIMINATORIA_DUPLA;
	
}
