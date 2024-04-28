package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.FormatoDTO;
import com.agon.tcc.model.Formato;
import com.agon.tcc.repository.FormatoRepository;

@Service
public class FormatoService {
	
	@Autowired
	private FormatoRepository formatoRepository;
	
    public List<FormatoDTO> findAll() {
    	return formatoRepository.findAll()
				.stream()
				.map(f -> new FormatoDTO(f.getCodigoFormato(), f.getDescricaoFormato()))
				.collect(Collectors.toList());
    }
    
    public FormatoDTO findById(Long id) {
		Optional<Formato> formato = formatoRepository.findById(id);
		if (formato.isPresent()) {
			Formato f = formato.get();
			return new FormatoDTO(f.getCodigoFormato(), f.getDescricaoFormato());
		} else {
			return null;
		}
	}
	
	@Transactional
	public void create(FormatoDTO formatoDTO) {
		formatoRepository.save(new Formato(formatoDTO));
	}
	
	@Transactional
	public void update(FormatoDTO formatoDTO) {
		Formato novoFormato = new Formato(findById(formatoDTO.codigoFormato()));
		novoFormato.setDescricaoFormato(formatoDTO.descricaoFormato());
		this.formatoRepository.save(novoFormato);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.formatoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}
    
}
