package com.agon.tcc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
    public static boolean checkPassword(String providedPassword, String storedPasswordHash) {
        return encoder.matches(providedPassword, storedPasswordHash);
    }
	
    public static String encryptPassword(String password) {
    	return encoder.encode(password);
    }
    
}