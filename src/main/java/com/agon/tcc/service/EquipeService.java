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
        return new EquipeDTO(equipe.getId(), equipe.getNome(), Util.convertToString(equipe.getImagem()), 
//        					 equipe.getModalidade(), equipe.getEquipeGrupos(), equipe.getUsuarios(), equipe.getDadosPartidas());
        					 equipe.getModalidade(), equipe.getEquipeGrupos(), equipe.getDadosPartidas());
    }
	
	public List<EquipeDTO> findAll() {
		return equipeRepository.findAll()
				.stream()
				.map(e -> {
					try {
						return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade(),
//											 e.getEquipeGrupos(), e.getUsuarios(), e.getDadosPartidas());
											 e.getEquipeGrupos(), e.getDadosPartidas());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
//					return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade() , e.getEquipeGrupos(), e.getUsuarios(), e.getDadosPartidas());
					return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade() , e.getEquipeGrupos(), e.getDadosPartidas());
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
	
	@Transactional
	public void create(EquipeDTO equipeDTO) {
//		Modalidade modalidade = modalidadeRepository.findById(equipeDTO.modalidade().getId())
//                .orElseThrow(() -> new EntityNotFoundException("Modalidade not found: " + equipeDTO.modalidade().getId()));
		
//        Equipe equipe = new Equipe();
//        equipe.setNome(equipeDTO.nome());
//        try {
//        	equipe.setImagem(Util.convertToByte(equipeDTO.imagem()));
//        } catch (Exception e) {
//			throw new RuntimeErrorException(null, "Não foi possível atualizar a grupo " + equipe.getId() + e);
//		}
//        equipe.setModalidade(modalidade);
//        equipe.setEquipeGrupos(equipeDTO.equipeGrupos());
//        equipe.setUsuarios(equipeDTO.usuarios());
//        equipe.setPartidas(equipeDTO.partidas());
		equipeRepository.save(new Equipe(equipeDTO));
	}
	
	@Transactional
	public void update(EquipeDTO equipeDTO) {
		Equipe equipe = new Equipe(findById(equipeDTO.id()));
		equipe.setNome(equipeDTO.nome());
		try {
			equipe.setImagem(Util.convertToByte(equipeDTO.imagem()));
    		this.equipeRepository.save(equipe);
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Não foi possível atualizar a grupo " + equipe.getId() + e);
		}
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