package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.repository.EquipeRepository;
import com.agon.tcc.util.Util;

import jakarta.transaction.Transactional;

@Service
public class EquipeService {

	@Autowired
	private EquipeRepository equipeRepository;
	
	private EquipeDTO converteDados(Equipe equipe) throws Exception {
        return new EquipeDTO(equipe.getId(), equipe.getNome(), Util.convertToString(equipe.getImagem()), equipe.getModalidade(), equipe.getUsuarios(), equipe.getPartidas());
    }
	
	public List<EquipeDTO> findAll() {
		return equipeRepository.findAll()
				.stream()
				.map(e -> {
					try {
						return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade(), e.getUsuarios(), e.getPartidas());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade(), e.getUsuarios(), e.getPartidas());
				})
				.collect(Collectors.toList());
	}
	
	public EquipeDTO findById(Long id) {
		Optional<Equipe> equipe = equipeRepository.findById(id);
		if (equipe.isPresent()) {
			Equipe eq = equipe.get();
			try {
				return converteDados(eq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public List<EquipeDTO> findAllEquipesByIdCampeonato(Long id) {
		return equipeRepository.findByCampeonatoId(id)
				.stream()
				.map(e -> {
					try {
						return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade(), e.getUsuarios(), e.getPartidas());
					} catch (Exception ex) {
						throw new RuntimeErrorException(null, "Erro na consulta: " + ex);
					}
//					return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade(), e.getUsuarios(), e.getPartidas());
				})
				.collect(Collectors.toList());
	}
	
	public List<EquipeDTO> findAllEquipesByIdAtletica(Long id) {
		return equipeRepository.findAllEquipesByIdAtletica(id)
				.stream()
				.map(e -> {
					try {
						return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade(), e.getUsuarios(), e.getPartidas());
					} catch(Exception ex) {
						throw new RuntimeErrorException(null, "Erro na consulta: " + ex);
					}
				}).collect(Collectors.toList());
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