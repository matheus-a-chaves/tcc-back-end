package com.agon.tcc.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agon.tcc.dto.MembroDTO;
import com.agon.tcc.model.Membro;
import com.agon.tcc.repository.MembroRepository;

@Service
public class MembroService {

	@Autowired
	private MembroRepository membroRepository;
	
	private MembroDTO converteDados(Membro membro) throws Exception {
		return new MembroDTO(membro.getId(), membro.getIdEquipe(), membro.getIdAtletica(), membro.getIdJogador());
    }
	
	public MembroDTO findById(Long id) {
		Optional<Membro> membro = membroRepository.findById(id);
		if (membro.isPresent()) {
			Membro aux = membro.get();
			try {
				return converteDados(aux);
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
		return null;
	}
	
	public MembroDTO findByIdEquipeAndIdAtleticaAndIdJogador(Long idEquipe, Long idAtletica, Long idJogador) {
		Optional<Membro> membro = membroRepository.findByIdEquipeAndIdAtleticaAndIdJogador(idEquipe, idAtletica, idJogador);
		if (membro.isPresent()) {
			Membro aux = membro.get();
			try {
				return converteDados(aux);
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
		return null;
	}
	
	public void save(Membro membro) {
		this.membroRepository.save(membro);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.membroRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}
	
}
