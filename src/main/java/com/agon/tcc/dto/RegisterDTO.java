package com.agon.tcc.dto;

public record RegisterDTO (String login,
							String password,
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
