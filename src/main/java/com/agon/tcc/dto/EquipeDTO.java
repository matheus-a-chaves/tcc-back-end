package com.agon.tcc.dto;

import java.util.List;

import com.agon.tcc.model.Modalidade;
import com.agon.tcc.model.Partida;
import com.agon.tcc.model.Usuario;

public record EquipeDTO(Long id, 
						String nome, 
						String imagem,
						Modalidade modalidade,
						List<Usuario> usuarios,
						List<Partida> partidas) {

}
