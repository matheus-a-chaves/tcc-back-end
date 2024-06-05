package com.agon.tcc.model;

import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.dto.UsuarioDTO;
import com.agon.tcc.util.Util;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String cpf;
	private String cnpj;
	private byte[] imagemPerfil;
	
	@Embedded
	private Long endereco;
	
	public Usuario(UsuarioDTO usuarioDTO) {
		this.id = usuarioDTO.id();
		this.nome = usuarioDTO.nome();
		this.cpf = usuarioDTO.cpf();
		this.cnpj = usuarioDTO.cnpj();
		try {
			this.imagemPerfil = Util.convertToByte(usuarioDTO.imagemPerfil());
		} catch (Exception e) {
			this.imagemPerfil = null;
		}
	}

}
