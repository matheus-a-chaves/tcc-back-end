package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.EtapaCampeonatoDTO;
import com.agon.tcc.model.EtapaCampeonato;
import com.agon.tcc.repository.EtapaCampeonatoRepository;

@Service
public class EtapaCampeonatoService {

	@Autowired
	private EtapaCampeonatoRepository etapaCampeonatoRepository;
	
	public List<EtapaCampeonatoDTO> findAll() {
    	return etapaCampeonatoRepository.findAll()
				.stream()
				.map(ec -> new EtapaCampeonatoDTO(ec.getId(), ec.getNomeEtapa(), ec.getCampeonato()))
				.collect(Collectors.toList());
    }
	
	public EtapaCampeonatoDTO findById(Long id) {
		Optional<EtapaCampeonato> etapaCampeonato = etapaCampeonatoRepository.findById(id);
		if (etapaCampeonato.isPresent()) {
			EtapaCampeonato ec = etapaCampeonato.get();
			return new EtapaCampeonatoDTO(ec.getId(), ec.getNomeEtapa(), ec.getCampeonato());
		} else {
			return null;
		}
	}
	
    public List<EtapaCampeonatoDTO> findByCampeonato(Long id) {
    	return etapaCampeonatoRepository.findByCampeonato(id)
				.stream()
				.map(ec -> new EtapaCampeonatoDTO(ec.getId(), ec.getNomeEtapa(), ec.getCampeonato()))
				.collect(Collectors.toList());
    }
    
	public EtapaCampeonatoDTO findByEtapaCampeonato(String nomeEtapa, Long id) {
		Optional<EtapaCampeonato> etapaCampeonato = etapaCampeonatoRepository.findByEtapaCampeonato(nomeEtapa, id);
		if (etapaCampeonato.isPresent()) {
			EtapaCampeonato ec = etapaCampeonato.get();
			return new EtapaCampeonatoDTO(ec.getId(), ec.getNomeEtapa(), ec.getCampeonato());
		} else {
			return null;
		}
	}
    
	@Transactional
	public void create(EtapaCampeonato etapaCampeonato) {
		etapaCampeonatoRepository.save(etapaCampeonato);
	}
	
	@Transactional
	public void update(EtapaCampeonatoDTO etapaCampeonatoDTO) {
		EtapaCampeonato novaEtapa = new EtapaCampeonato(findByEtapaCampeonato(etapaCampeonatoDTO.nomeEtapa(), etapaCampeonatoDTO.campeonato().getId()));
		this.etapaCampeonatoRepository.save(novaEtapa);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.etapaCampeonatoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}

}
