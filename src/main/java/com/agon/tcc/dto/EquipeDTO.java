package com.agon.tcc.dto;

import com.agon.tcc.model.Usuario;

public record EquipeDTO(Long id, 
						String nome, 
						String imagem, 
						String modalidade,
						Usuario usuario) {

}
