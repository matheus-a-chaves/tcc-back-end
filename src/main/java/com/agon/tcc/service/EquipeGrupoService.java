package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agon.tcc.dto.EquipeGrupoDTO;
import com.agon.tcc.model.EquipeGrupo;
import com.agon.tcc.repository.EquipeGrupoRepository;

import jakarta.transaction.Transactional;

@Service
public class EquipeGrupoService {

	@Autowired
	private EquipeGrupoRepository equipeGrupoRepository;
	
	private EquipeGrupoDTO converteDados(EquipeGrupo equipeGrupo) throws Exception {
        return new EquipeGrupoDTO(equipeGrupo.getId(), equipeGrupo.getEquipe(), equipeGrupo.getGrupo(), equipeGrupo.getPontos(), equipeGrupo.getQtdJogos(), 
        						  equipeGrupo.getVitorias(), equipeGrupo.getEmpates(), equipeGrupo.getDerrotas(), equipeGrupo.getSaldoGols());
    }
	
	public List<EquipeGrupoDTO> findAll() {
		return equipeGrupoRepository.findAll()
				.stream()
				.map(e -> {
					try {
						return new EquipeGrupoDTO(e.getId(), e.getEquipe(), e.getGrupo(), e.getPontos(), e.getQtdJogos(), 
      						  					  e.getVitorias(), e.getEmpates(), e.getDerrotas(), e.getSaldoGols());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					return new EquipeGrupoDTO(e.getId(), e.getEquipe(), e.getGrupo(), e.getPontos(), e.getQtdJogos(), 
		  					  				  e.getVitorias(), e.getEmpates(), e.getDerrotas(), e.getSaldoGols());
				})
				.collect(Collectors.toList());
	}
	
	public EquipeGrupoDTO findById(Long id) {
		Optional<EquipeGrupo> equipeGrupo = equipeGrupoRepository.findById(id);
		if (equipeGrupo.isPresent()) {
			EquipeGrupo eq = equipeGrupo.get();
			try {
				return converteDados(eq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Transactional
	public void create(EquipeGrupoDTO equipeGrupoDTO) {
		equipeGrupoRepository.save(new EquipeGrupo(equipeGrupoDTO));
	}
	
	@Transactional
	public void update(EquipeGrupoDTO equipeGrupoDTO) {
		EquipeGrupo equipeGrupo = new EquipeGrupo(findById(equipeGrupoDTO.id()));
		equipeGrupo.setPontos(equipeGrupoDTO.pontos());
		equipeGrupo.setVitorias(equipeGrupoDTO.vitorias());
		equipeGrupo.setEmpates(equipeGrupoDTO.empates());
		equipeGrupo.setDerrotas(equipeGrupoDTO.derrotas());
		equipeGrupo.setSaldoGols(equipeGrupoDTO.saldoGols());
        
        try {
    		this.equipeGrupoRepository.save(equipeGrupo);
        } catch (Exception e) {
			throw new RuntimeErrorException(null, "Não foi possível atualizar a grupo " + equipeGrupo.getId());
		}
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.equipeGrupoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}
	
}
