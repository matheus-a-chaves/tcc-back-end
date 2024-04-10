package com.agon.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Modalidade {
	
	FUTEBOL(1, "FUTEBOL"),
	FUTSAL(2, "FUTSAL"),
	FUT7(3, "FUT7"),
	BASQUETE(4, "BASQUETE"),
	VOLEI(5, "VOLEI"),
	HANDEBOL(6, "HANDEBOL");
	
	private Integer id;
	private String descricao;

}
