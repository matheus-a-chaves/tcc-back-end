package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.model.Campeonato;
import com.agon.tcc.repository.CampeonatoRepository;

@Service
public class CampeonatoService {

	@Autowired
	private CampeonatoRepository campeonatoRepository;

	public List<Campeonato> findAll() {
		return this.campeonatoRepository.findAll();
	}
	
	public Campeonato findById(Long id) {
		Optional<Campeonato> campeonato = this.campeonatoRepository.findById(id);
		return campeonato.orElseThrow(() -> new RuntimeException("Campeonato " + id + " não encontrado!"));
	}
	
	@Transactional
	public Campeonato create(Campeonato camp) {
		camp.setId(null);
		camp = campeonatoRepository.save(camp);
		return camp;
	}
	
	@Transactional
	public Campeonato update(Campeonato camp) {
		Campeonato newCamp = findById(camp.getId());
		newCamp.setNome(camp.getNome());
		newCamp.setDataInicio(camp.getDataInicio());
		newCamp.setQuantidadeEquipes(camp.getQuantidadeEquipes());
		return this.campeonatoRepository.save(newCamp);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			this.campeonatoRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
		}
	}
	
}
