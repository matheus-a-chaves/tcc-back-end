package com.agon.tcc.dto;

import java.util.Date;

public record RegisterDTO (String login,
							String password,
							String nome,
							String docIdentificacao,
							Date dataNascimento,
							String bairro,
							String cep,
							String cidade,
							String estado,
							Integer numero,
							String rua,
							Integer tipoUsuario) {

}
