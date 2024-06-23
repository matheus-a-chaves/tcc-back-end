package com.agon.tcc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {

    // Gera um salt aleatório
    public static String gerarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return bytesToHex(saltBytes);
    }
    
    // Criptografa a senha com SHA-256 e o salt
    public static String criptografarSenha(String senha, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] saltBytes = hexToBytes(salt);
            byte[] senhaBytes = senha.getBytes();
            byte[] concatBytes = new byte[saltBytes.length + senhaBytes.length];
            System.arraycopy(saltBytes, 0, concatBytes, 0, saltBytes.length);
            System.arraycopy(senhaBytes, 0, concatBytes, saltBytes.length, senhaBytes.length);
            byte[] hashBytes = digest.digest(concatBytes);
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String criptografarPassword(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] senhaBytes = senha.getBytes();
            byte[] hashBytes = digest.digest(senhaBytes);
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Converte bytes para representação hexadecimal
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // Converte representação hexadecimal para bytes
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static void main(String[] args) {
        String senha = "123456";
        String salt = gerarSalt();
        String senhaCriptografada = criptografarSenha(senha, salt);

        System.out.println("Senha original: " + senha);
        System.out.println("Salt: " + salt);
        System.out.println("Senha criptografada: " + senhaCriptografada);
    }
	
}
