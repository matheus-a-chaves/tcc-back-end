package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.dto.MembroDTO;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.Membro;
import com.agon.tcc.repository.EquipeRepository;
import com.agon.tcc.repository.MembroRepository;
import com.agon.tcc.util.Util;

import jakarta.transaction.Transactional;

@Service
public class EquipeService {

	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private MembroService membroService;
	
	@Autowired
	private MembroRepository membroRepository;
		
	private EquipeDTO converteDados(Equipe equipe) throws Exception {
        //return new EquipeDTO(equipe.getId(), equipe.getNome(), Util.convertToString(equipe.getImagem()), equipe.getModalidade(), equipe.getEquipeGrupos(), equipe.getDadosPartidas());
		return new EquipeDTO(equipe.getId(), equipe.getNome(), Util.convertToString(equipe.getImagem()), equipe.getModalidade());
    }
	
	public List<EquipeDTO> findAll() {
		return equipeRepository.findAll()
				.stream()
				.map(e -> {
					try {
						//return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade(), e.getEquipeGrupos(), e.getDadosPartidas());
						return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					//return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade() , e.getEquipeGrupos(), e.getDadosPartidas());
					return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade());
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
	
	public EquipeDTO findByNome(String nome) {
		Optional<Equipe> equipe = equipeRepository.findByNome(nome);
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
	
	public List<EquipeDTO> findAllTimesByIdCampeonato(Long id) {
		return equipeRepository.findAllTimesByIdCampeonato(id)
				.stream()
				.map(e -> {
					try {
						//return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade(), e.getEquipeGrupos(), e.getDadosPartidas());
						return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					//return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade() , e.getEquipeGrupos(), e.getDadosPartidas());
					return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade());
				})
				.collect(Collectors.toList());
	}
	
	public List<EquipeDTO> findAllTimesByIdAtletica(Long id) {
		return equipeRepository.findAllTimesByIdAtletica(id)
				.stream()
				.map(e -> {
					try {
						//return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade(), e.getEquipeGrupos(), e.getDadosPartidas());
						return new EquipeDTO(e.getId(), e.getNome(), Util.convertToString(e.getImagem()), e.getModalidade());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					//return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade() , e.getEquipeGrupos(), e.getDadosPartidas());
					return new EquipeDTO(e.getId(), e.getNome(), null, e.getModalidade());
				})
				.collect(Collectors.toList());
	}
	
	@Transactional
	public boolean adicionarJogador(Long idJogador, Long idEquipe, Long idAtletica) {
		try {
			MembroDTO membroDTO =  this.membroService.findByIdEquipeAndIdAtletica(idEquipe, idAtletica);
			if(membroDTO != null) {
				Membro membro = new Membro(this.membroService.findById(membroDTO.id()));
				if(membro.getIdJogador() == null) {
					membro.setIdJogador(idJogador);
					this.membroRepository.save(membro);
					
					return true;
				}
			}
		} catch(Exception ex) {
			return false;
		}
		return false;
	}
	
	@Transactional
	public boolean removerJogador(Long idEquipe, Long idAtletica) {
		try {
			MembroDTO membroDTO =  this.membroService.findByIdEquipeAndIdAtletica(idEquipe, idAtletica);
			if(membroDTO != null) {
				Membro membro = new Membro(this.membroService.findById(membroDTO.id()));
				if(membro.getIdJogador() != null) {
					membro.setIdJogador(null);
					this.membroRepository.save(membro);
					
					return true;
				}
			}
		}catch(Exception ex) {
			return false;
		}
		return false;
	}
	
	@Transactional
	public void create(EquipeDTO equipeDTO) {
		equipeRepository.save(new Equipe(equipeDTO));
	}
	
	@Transactional
	public void createMembros(Long idEquipe, Long idAtletica) {
		equipeRepository.saveMembros(idEquipe, idAtletica);
	}
	
	@Transactional
	public void update(EquipeDTO equipeDTO) {
		Equipe equipe = new Equipe(findById(equipeDTO.id()));
		equipe.setNome(equipeDTO.nome());
		equipe.setModalidade(equipeDTO.modalidade());
		try {
			equipe.setImagem(Util.convertToByte(equipeDTO.imagem()));
    		this.equipeRepository.save(equipe);
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Não foi possível atualizar a equipe " + equipe.getId() + e);
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