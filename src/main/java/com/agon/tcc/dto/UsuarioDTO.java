package com.agon.tcc.dto;

public record UsuarioDTO(Long id,
						String nome,
						String cpf,
						String cnpj,
						String imagemPerfil,
						String bairro,
						String cep,
						String cidade,
						String estado,
						Integer numero,
						String rua,
						Integer tipoUsuario) {
	
}
