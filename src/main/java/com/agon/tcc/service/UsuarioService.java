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
        return new UsuarioDTO(user.getId(), 
        					  user.getNome(), 
        					  user.getDataNascimento(),
        					  (user.getCpf() != null ? user.getCpf() : null), 
        					  (user.getCnpj() != null ? user.getCnpj() : null), 
        					  (user.getImagemPerfil() != null ? Util.convertToString(user.getImagemPerfil()) : null),
        					  user.getBairro(), 
        					  user.getCep(),
        					  user.getCidade(), 
        					  user.getEstado(),
        					  user.getNumero(),  
        					  user.getRua(),
        					  user.getTipoUsuario());
    }
	
	public List<UsuarioDTO> findAll() {
		return usuarioRepository.findAll()
				.stream()
				.map(user -> {
					try {
						return converteDados(user);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					return new UsuarioDTO(user.getId(), user.getNome(), user.getDataNascimento(), user.getCpf(), user.getCnpj(), null, user.getBairro(), user.getCep(),user.getCidade(), user.getEstado(),user.getNumero(),  user.getRua(),user.getTipoUsuario() );
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
	public void create(Usuario user) {
		usuarioRepository.save(user);
	}
	
	@Transactional
	public void update(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario(findById(usuarioDTO.id()));
		try {
			usuario.setNome(usuarioDTO.nome());
			usuario.setDataNascimento(usuarioDTO.dataNascimento());
			usuario.setBairro(usuarioDTO.bairro());
			usuario.setCep(usuarioDTO.cep());
			usuario.setCidade(usuarioDTO.cidade());
			usuario.setEstado(usuarioDTO.estado());
			usuario.setNumero(usuarioDTO.numero());
			usuario.setRua(usuarioDTO.rua());

			if (!(usuarioDTO.imagemPerfil().isBlank())){
				usuario.setImagemPerfil(Util.convertToByte(usuarioDTO.imagemPerfil()));

			}
    		this.usuarioRepository.save(usuario);
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Não foi possível atualizar o usuario " + usuario.getId() + e);
		}
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
				.map(user -> {
					try {
						return converteDados(user);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					return new UsuarioDTO(user.getId(), user.getNome(), user.getDataNascimento(), user.getCpf(), user.getCnpj(), null, user.getBairro(), user.getCep(),user.getCidade(), user.getEstado(),user.getNumero(),  user.getRua(),user.getTipoUsuario() );
				})
				.collect(Collectors.toList());
	}
	
	public Usuario findByEquipe(Long idEquipe) {
		return usuarioRepository.findByEquipe(idEquipe);
	}
	
	public Usuario findByCpf(String cpf) {
		return usuarioRepository.findByCpf(cpf);
	}
	
	public Usuario findByCnpj(String cnpj) {
		return usuarioRepository.findByCnpj(cnpj);
	}
}
