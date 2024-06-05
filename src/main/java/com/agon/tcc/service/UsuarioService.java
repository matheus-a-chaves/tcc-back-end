package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agon.tcc.dto.UsuarioDTO;
import com.agon.tcc.model.Usuario;
import com.agon.tcc.repository.UsuarioRepository;
import com.agon.tcc.util.Util;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private UsuarioDTO converteDados(Usuario user) throws Exception {
        return new UsuarioDTO(user.getId(), user.getNome(), user.getCpf(), user.getCnpj(), Util.convertToString(user.getImagemPerfil()), user.getEndereco());
    }
	
	public List<UsuarioDTO> findAll() {
		return usuarioRepository.findAll()
				.stream()
				.map(u -> {
					try {
						return new UsuarioDTO(u.getId(), u.getNome(), u.getCpf(), u.getCnpj(), Util.convertToString(u.getImagemPerfil()), u.getEndereco());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					return new UsuarioDTO(u.getId(), u.getNome(), u.getCpf(), u.getCnpj(), null, u.getEndereco());
				})
				.collect(Collectors.toList());
	}
	
	public UsuarioDTO findById(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			Usuario user = usuario.get();
			try {
				return converteDados(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Transactional
	public void create(UsuarioDTO usuarioDTO) {
		usuarioRepository.save(new Usuario(usuarioDTO));
	}
	
	@Transactional
	public void update(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario(findById(usuarioDTO.id()));
		usuario.setNome(usuarioDTO.nome());
		usuario.setCpf(usuarioDTO.cpf());
		usuario.setCnpj(usuarioDTO.cnpj());
		try {
			usuario.setImagemPerfil(Util.convertToByte(usuarioDTO.imagemPerfil()));
    		this.usuarioRepository.save(usuario);
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Não foi possível atualizar o usuario " + usuario.getId() + e);
		}
		usuario.setEndereco(usuarioDTO.endereco());
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.usuarioRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}
	
	public List<UsuarioDTO> findAllJogadoresByEquipe(Long id) {
		return usuarioRepository.findAllJogadoresByEquipe(id)
				.stream()
				.map(u -> {
					try {
						return new UsuarioDTO(u.getId(), u.getNome(), u.getCpf(), u.getCnpj(), Util.convertToString(u.getImagemPerfil()), u.getEndereco());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					return new UsuarioDTO(u.getId(), u.getNome(), u.getCpf(), u.getCnpj(), null, u.getEndereco());
				})
				.collect(Collectors.toList());
	}

}
