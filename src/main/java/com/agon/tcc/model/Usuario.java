package com.agon.tcc.model;

import java.util.Date;

import com.agon.tcc.dto.UsuarioDTO;
import com.agon.tcc.util.Util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(name = "nome")
    private String nome;
    
	@Column(name = "cpf")
    private String cpf;
    
	@Column(name = "cnpj")
    private String cnpj;
    
	@Column(name = "imagem_perfil")
    private byte[] imagemPerfil;
    
	@Column(name = "bairro")
    private String bairro;
    
	@Column(name = "cep")
    private String cep;
    
	@Column(name = "cidade")
    private String cidade;
    
	@Column(name = "estado")
    private String estado;
    
	@Column(name = "numero")
    private Integer numero;
    
	@Column(name = "rua")
    private String rua;
	
	@Column(name = "tipo_usuario", nullable = false)
	private Integer tipoUsuario;
	
	@Column(name = "dt_Nascimento")
    private Date dataNascimento;
	
	
	public Usuario(String nome, Date dataNascimento,String cpf, String cnpj, String imagemPerfil, String bairro, String cep, String cidade, String estado, Integer numero, String rua) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.cnpj = cnpj;
        try {
            this.imagemPerfil = Util.convertToByte(imagemPerfil);
        } catch (Exception e) {
            this.imagemPerfil = null;
        }
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
        this.rua = rua;
	}	
	
    public Usuario(UsuarioDTO usuarioDTO) {
        this.id = usuarioDTO.id();
        this.nome = usuarioDTO.nome();
        this.dataNascimento = usuarioDTO.dataNascimento();
        this.cpf = usuarioDTO.cpf();
        this.cnpj = usuarioDTO.cnpj();
        this.bairro = usuarioDTO.bairro();
        this.cep = usuarioDTO.cep();
        this.cidade = usuarioDTO.cidade();
        this.estado = usuarioDTO.estado();
        this.numero = usuarioDTO.numero();
        this.rua = usuarioDTO.rua();
        this.tipoUsuario = usuarioDTO.tipoUsuario();
        try {
            this.imagemPerfil = Util.convertToByte(usuarioDTO.imagemPerfil());
        } catch (Exception e) {
            this.imagemPerfil = null;
        }
    }

}
