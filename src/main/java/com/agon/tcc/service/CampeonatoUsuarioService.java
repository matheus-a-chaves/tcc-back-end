package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.CampeonatoUsuarioDTO;
import com.agon.tcc.model.CampeonatoUsuario;
import com.agon.tcc.repository.CampeonatoUsuarioRepository;


@Service
public class CampeonatoUsuarioService {
	
	@Autowired
	private CampeonatoUsuarioRepository campeonatoUsuarioRepository;
	
	public List<CampeonatoUsuarioDTO> findAll() {
    	return campeonatoUsuarioRepository.findAll()
				.stream()
				.map(c -> new CampeonatoUsuarioDTO(c.getId(), c.getIdCampeonato(), c.getIdAtletica(), c.getIdCriador()))
				.collect(Collectors.toList());
    }
    
    public CampeonatoUsuarioDTO findById(Long id) {
		Optional<CampeonatoUsuario> campeonatoUsuario = campeonatoUsuarioRepository.findById(id);
		if (campeonatoUsuario.isPresent()) {
			CampeonatoUsuario c = campeonatoUsuario.get();
			return new CampeonatoUsuarioDTO(c.getId(), c.getIdCampeonato(), c.getIdAtletica(), c.getIdCriador());
		} else {
			return null;
		}
	}
    
    public CampeonatoUsuario findByIdCampeonatoAndIdAtletica(Long idCampeonato, Long idAtletica) {
		Optional<CampeonatoUsuario> campeonatoUsuario = campeonatoUsuarioRepository.findByIdCampeonatoAndIdAtletica(idCampeonato, idAtletica);
		if (campeonatoUsuario.isPresent()) {
			CampeonatoUsuario c = campeonatoUsuario.get();
			return c;
		} else {
			return null;
		}
	}
	
	@Transactional
	public void create(CampeonatoUsuarioDTO campeonatoUsuarioDTO) {
		this.campeonatoUsuarioRepository.save(new CampeonatoUsuario(campeonatoUsuarioDTO));
	}
	
	@Transactional
	public void create(CampeonatoUsuario campeonatoUsuario) {
		this.campeonatoUsuarioRepository.save(campeonatoUsuario);
	}
	
	@Transactional
	public void update(CampeonatoUsuarioDTO campeonatoUsuarioDTO) {
		CampeonatoUsuario novoCampeonatoUsuario = new CampeonatoUsuario(findById(campeonatoUsuarioDTO.id()));
		this.campeonatoUsuarioRepository.save(novoCampeonatoUsuario);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.campeonatoUsuarioRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}

}
