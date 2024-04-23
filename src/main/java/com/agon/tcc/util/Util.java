package com.agon.tcc.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

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

}
