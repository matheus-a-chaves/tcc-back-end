package com.agon.tcc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Modalidade")
public enum Modalidade {
	
	FUTEBOL(1),
	FUTSAL(2),
	FUT7(3),
	BASQUETE(4),
	VOLEI(5),
	HANDEBOL(6);

	Modalidade(int i) {
	}
}
