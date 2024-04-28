package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.repository.EquipeRepository;


@Service
public class EquipeService {

	@Autowired
	private EquipeRepository equipeRepository;
	
	public List<EquipeDTO> findAll() {
		return equipeRepository.findAll()
				.stream()
				.map(e -> new EquipeDTO(e.getId(), e.getNome(), e.getImagem(), e.getModalidade(), e.getUsuario()))
				.collect(Collectors.toList());
	}
	
	public EquipeDTO findById(Long id) {
		Optional<Equipe> equipe = equipeRepository.findById(id);
		if (equipe.isPresent()) {
			Equipe e = equipe.get();
			return new EquipeDTO(e.getId(), e.getNome(), e.getImagem(), e.getModalidade(), e.getUsuario());
		} else {
			return null;
		}
	}
	
	public List<EquipeDTO> findAllEquipesByIdCampeonato(Long id) {
		return equipeRepository.findAllEquipesByIdCampeonato(id)
				.stream()
				.map(e -> new EquipeDTO(e.getId(), e.getNome(), e.getImagem(), e.getModalidade(), e.getUsuario()))
				.collect(Collectors.toList());
	}
	
	public List<EquipeDTO> findAllEquipesByIdAtletica(Long id) {
		return equipeRepository.findAllEquipesByIdAtletica(id)
				.stream()
				.map(e -> new EquipeDTO(e.getId(), e.getNome(), e.getImagem(), e.getModalidade(), e.getUsuario()))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void create(EquipeDTO equipeDTO) {
		equipeRepository.save(new Equipe(equipeDTO));
	}
	
	@Transactional
	public void update(EquipeDTO equipeDTO) {
		Equipe novaEquipe = new Equipe(findById(equipeDTO.id()));
		novaEquipe.setNome(equipeDTO.nome());
		this.equipeRepository.save(novaEquipe);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.equipeRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}
	
}
