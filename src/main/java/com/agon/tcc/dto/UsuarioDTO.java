package com.agon.tcc.dto;

import java.util.Date;

public record UsuarioDTO(Long id,
						String nome,
						Date dataNascimento,
						String cpf,
						String cnpj,
						String imagemPerfil,
						String bairro,
						String cep,
						String cidade,
						String estado,
						Integer numero,
						String rua,
						Integer tipoUsuario,
						String salt) {
	
}
