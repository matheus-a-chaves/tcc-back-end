package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.model.Equipe;
import com.agon.tcc.repository.EquipeRepository;


@Service
public class EquipeService {

	@Autowired
	private EquipeRepository equipeRepository;
	
	public List<Equipe> findAll() {
		return this.equipeRepository.findAll();
	}
	
	public Equipe findById(Long id) {
		Optional<Equipe> equipe = this.equipeRepository.findById(id);
		return equipe.orElseThrow(() -> new RuntimeException("Equipe " + id + " não encontrado!"));
	}
	
	@Transactional
	public Equipe create(Equipe equipe) {
		equipe.setId(null);
		equipe = equipeRepository.save(equipe);
		return equipe;
	}
	
	@Transactional
	public Equipe update(Equipe equipe) {
		Equipe newEquipe = findById(equipe.getId());
		newEquipe.setNome(equipe.getNome());
		newEquipe.setImagemEquipe(equipe.getImagemEquipe());
		return this.equipeRepository.save(newEquipe);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			this.equipeRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
		}
	}
	
}
