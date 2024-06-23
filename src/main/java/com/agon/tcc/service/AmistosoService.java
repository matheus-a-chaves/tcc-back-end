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
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.SolicitacaoAmistoso;
import com.agon.tcc.model.enums.StatusSolicitacao;
import com.agon.tcc.repository.AmistosoRepository;

@Service
public class AmistosoService {

	@Autowired
	private AmistosoRepository amistosoRepository;
	
	@Autowired
	private PartidaService partidaService;
	
	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private SolicitacaoAmistosoService solicitacaoAmistosoService;
				
	public List<AmistosoDTO> findAll() {
		return amistosoRepository.findAll()
				.stream()
				.map(a -> new AmistosoDTO(a.getId(), a.getDataHorario(), a.getStatusAmistoso(), a.getModalidade(), null))
				.collect(Collectors.toList());
	}
	
	public AmistosoDTO findById(Long id) {
		Optional<Amistoso> amistoso = amistosoRepository.findById(id);
		if (amistoso.isPresent()) {
			Amistoso a = amistoso.get();
			return new AmistosoDTO(a.getId(), a.getDataHorario(), a.getStatusAmistoso(), a.getModalidade(), null);
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
		amistoso.setSolicitacaoAmistoso(amistoso.getSolicitacaoAmistoso());
		this.amistosoRepository.save(amistoso);
	}

	@Transactional
	public void update(Amistoso amistoso) {
		amistoso.setStatusAmistoso(amistoso.getStatusAmistoso());
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
	public void criarAmistoso(Long idAtletica, Long idEquipeVisitante, AmistosoDTO amistosoDTO) {        
        Amistoso amistoso = new Amistoso();
        amistoso.setDataHorario(amistosoDTO.dataHora());
        amistoso.setStatusAmistoso(StatusSolicitacao.PENDENTE);
        amistoso.setModalidade(amistosoDTO.modalidade());
        amistosoRepository.save(amistoso);
        
        SolicitacaoAmistoso solicitacao = new SolicitacaoAmistoso();
        solicitacao.setDataSolicitacao(amistoso.getDataHorario());
        solicitacao.setAmistoso(amistoso);
        
        Equipe equipe = new Equipe(equipeService.findByAtleticaAndModalidade(idAtletica, amistosoDTO.modalidade().getId()));
        solicitacao.setEquipeVisitante(equipe);
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        solicitacaoAmistosoService.create(solicitacao);

		partidaService.gerarPartida(idAtletica, idEquipeVisitante, amistosoDTO.endereco(), amistoso);
    }
	
}
