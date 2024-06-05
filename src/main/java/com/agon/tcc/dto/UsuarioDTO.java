package com.agon.tcc.dto;

public record UsuarioDTO(Long id,
						String nome,
						String cpf,
						String cnpj,
						String imagemPerfil,
						Long endereco) {
	
}
