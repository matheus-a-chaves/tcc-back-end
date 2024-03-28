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
		Campeonato newCamp = new Campeonato();
		return newCamp.listCampeonato;
//		return this.campeonatoRepository.findAll();
	}
	
	public Campeonato findById(Long id) {
		Campeonato newCamp = new Campeonato();
		for(Campeonato camp : newCamp.listCampeonato) {
			if(camp.getId().compareTo(id) == 0) {
				return camp;
			}
		}
		
		return newCamp;
		
//		Optional<Campeonato> campeonato = this.campeonatoRepository.findById(id);
//		return campeonato.orElseThrow(() -> new RuntimeException("Campeonato " + id + " não encontrado!"));
	}
	
	@Transactional
	public Campeonato create(Campeonato camp) {
		Campeonato newCamp = new Campeonato();
		
		int aux = newCamp.listCampeonato.size() + 1;
		Long aux2 = Long.parseLong(Integer.toString(aux));
		camp.setId(aux2);
		newCamp.listCampeonato.add(camp);
//		camp = campeonatoRepository.save(camp);
		return camp;
	}
	
	@Transactional
	public void update(Campeonato camp) {
		Campeonato newCamp = new Campeonato();
		newCamp.listCampeonato.removeIf(t -> t.getId().compareTo(camp.getId()) == 0);
		newCamp.listCampeonato.add(newCamp);
//		Campeonato newCamp = findById(camp.getId());
//		newCamp.setNome(camp.getNome());
//		newCamp.setDataInicio(camp.getDataInicio());
//		newCamp.setQuantidadeEquipes(camp.getQuantidadeEquipes());
//		return this.campeonatoRepository.save(newCamp);
	}
	
	public void delete(Long id) {
		Campeonato newCamp = new Campeonato();
		if(newCamp.listCampeonato.removeIf(t -> t.getId().compareTo(id) == 0)) {
			
		}else {
			throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
		}
//		findById(id);
//		try {
//			this.campeonatoRepository.deleteById(id);
//		} catch (Exception e) {
//			throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
//		}
	}
	
}
