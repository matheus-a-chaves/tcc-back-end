package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.AmistosoDTO;
import com.agon.tcc.model.Amistoso;
import com.agon.tcc.model.Partida;
import com.agon.tcc.repository.AmistosoRepository;

@Service
public class AmistosoService {

	@Autowired
	private AmistosoRepository amistosoRepository;
	
	@Autowired
	private PartidaService partidaService;
				
	public List<AmistosoDTO> findAll() {
		return amistosoRepository.findAll()
				.stream()
				.map(a -> new AmistosoDTO(a.getId(), a.getDataHorario(), a.getModalidade(), a.getPartida()))
				.collect(Collectors.toList());
	}
	
	public AmistosoDTO findById(Long id) {
		Optional<Amistoso> amistoso = amistosoRepository.findById(id);
		if (amistoso.isPresent()) {
			Amistoso a = amistoso.get();
			return new AmistosoDTO(a.getId(), a.getDataHorario(), a.getModalidade(), a.getPartida());
		}
		return null;
	}
	
	@Transactional
	public void create(AmistosoDTO amistosoDTO) {
		amistosoRepository.save(new Amistoso(amistosoDTO));
	}
	
	@Transactional
	public void update(AmistosoDTO amistosoDTO) {
		Amistoso amistoso = new Amistoso(findById(amistosoDTO.id()));
		amistoso.setPartida(amistosoDTO.partida());;
		this.amistosoRepository.save(amistoso);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.amistosoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}
	
	/**
	 * Método para gerar o Amistoso
	 * @param amistosoDTO
	 */
	public void criarAmistoso(AmistosoDTO amistosoDTO) {
		Partida partida = partidaService.gerarPartida(amistosoDTO);
        
        Amistoso amistoso = new Amistoso();
        amistoso.setDataHorario(amistosoDTO.dataHora());
        amistoso.setStatus("PENDENTE");
        amistoso.setModalidade(amistosoDTO.modalidade());
        amistoso.setPartida(partida);

        amistosoRepository.save(amistoso);
    }
}
