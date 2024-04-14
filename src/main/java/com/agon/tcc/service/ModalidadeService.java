package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.ModalidadeDTO;
import com.agon.tcc.model.Modalidade;
import com.agon.tcc.repository.ModalidadeRepository;

@Service
public class ModalidadeService {
	
	@Autowired
	private ModalidadeRepository modalidadeRepository;
	
    public List<ModalidadeDTO> findAll() {
    	return modalidadeRepository.findAll()
				.stream()
				.map(m -> new ModalidadeDTO(m.getCodigoModalidade(), m.getDescricaoModalidade()))
				.collect(Collectors.toList());
    }
    
    public ModalidadeDTO findById(Long id) {
		Optional<Modalidade> modalidade = modalidadeRepository.findById(id);
		if (modalidade.isPresent()) {
			Modalidade m = modalidade.get();
			return new ModalidadeDTO(m.getCodigoModalidade(), m.getDescricaoModalidade());
		} else {
			return null;
		}
	}
	
	@Transactional
	public void create(ModalidadeDTO modalidadeDTO) {
		modalidadeRepository.save(new Modalidade(modalidadeDTO));
	}
	
	@Transactional
	public void update(ModalidadeDTO modalidadeDTO) {
		Modalidade novaModalidade = new Modalidade(findById(modalidadeDTO.codigoModalidade()));
		novaModalidade.setDescricaoModalidade(modalidadeDTO.descricaoModalidade());
		this.modalidadeRepository.save(novaModalidade);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.modalidadeRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}
	
}
