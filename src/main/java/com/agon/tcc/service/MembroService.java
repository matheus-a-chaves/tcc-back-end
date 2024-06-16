package com.agon.tcc.service;

import java.util.Optional;

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
	
	public MembroDTO findByIdEquipeAndIdAtletica(Long idEquipe, Long idAtletica) {
		Optional<Membro> membro = membroRepository.findByIdEquipeAndIdAtletica(idEquipe, idAtletica);
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
	
}
