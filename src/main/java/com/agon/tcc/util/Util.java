package com.agon.tcc.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.agon.tcc.dto.RegisterDTO;
import com.agon.tcc.model.Usuario;

public class Util {
	
	public static byte[] convertToByte(String base64String) throws Exception {
		try {
			byte[] objConvertido = Base64.getDecoder().decode(new String(base64String).getBytes("UTF-8"));
			return objConvertido;
		} catch (UnsupportedEncodingException e) {
			throw new Exception("Não foi possível converter: " + e.getMessage());
		}
	}
	
	public static String convertToString(byte[] base64Byte) throws Exception {
		try {
			String objConvertido = Base64.getEncoder().encodeToString(base64Byte);
			return objConvertido;
		} catch(UnsupportedOperationException e) {
			throw new Exception("Não foi possível converter: " + e.getMessage());
		}
	}
	
	public static Usuario createAndValidateTipoUsuario(RegisterDTO data) {
		
		Usuario usuario = new Usuario(data.nome(), data.dataNascimento(), null, null, null, data.bairro(), data.cep(), data.cidade(), data.estado(), data.numero(), data.rua());
		
		if(data.docIdentificacao().length() == 11) {
			usuario.setTipoUsuario(2);
			
			usuario.setCpf(data.docIdentificacao());
			usuario.setCnpj(null);
		} else {
			usuario.setTipoUsuario(1);
			
			usuario.setCnpj(data.docIdentificacao());
			usuario.setCpf(null);
		}
		
		return usuario;
	}

}