package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.model.Usuario;
import com.agon.tcc.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> findAll() {
		return this.usuarioRepository.findAll();
	}
	
	public Usuario findById(Long id) {
		Optional<Usuario> usuario = this.usuarioRepository.findById(id);
		return usuario.orElseThrow(() -> new RuntimeException("Usuario " + id + " não encontrado!"));
	}
	
//	@Transactional
//	public Usuario create(Usuario user) {
//		user.setId(null);
//		user = usuarioRepository.save(user);
//		return user;
//	}
	
	@Transactional
	public Usuario update(Usuario user) {
		Usuario newUser = findById(user.getId());
		newUser.setNome(user.getNome());
		newUser.setCpf(user.getCpf());
		newUser.setCnpj(user.getCnpj());
		newUser.setImagemPerfil(user.getImagemPerfil());
		return this.usuarioRepository.save(newUser);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			this.usuarioRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
		}
	}

}
