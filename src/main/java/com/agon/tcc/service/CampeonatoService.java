package com.agon.tcc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import com.agon.tcc.repository.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.CampeonatoDTO;
import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.dto.EtapaCampeonatoDTO;
import com.agon.tcc.model.Campeonato;
import com.agon.tcc.model.CampeonatoUsuario;
import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.EtapaCampeonato;
import com.agon.tcc.model.Partida;
import com.agon.tcc.model.PartidaChaveamento;
import com.agon.tcc.model.Resultado;
import com.agon.tcc.model.Usuario;
import com.agon.tcc.repository.CampeonatoRepository;
import com.agon.tcc.repository.EquipeRepository;
import com.agon.tcc.util.Util;

@Service
public class CampeonatoService {

	@Autowired
	private CampeonatoRepository campeonatoRepository;
	
	@Autowired
    private EquipeService equipeService;

    @Autowired
    private EtapaCampeonatoService etapaCampeonatoService;

    @Autowired
    private PartidaService partidaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private CampeonatoUsuarioService campeonatoUsuarioService;
    
    @Autowired
    private DadosPartidaService dadosPartidaService;

	@Autowired
	private MembroRepository membroRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	private CampeonatoDTO converteDados(Campeonato camp) throws Exception {
        return new CampeonatoDTO(camp.getId(), camp.getNome(), camp.getQuantidadeEquipes(), camp.getDataInicio(), camp.getDataFim(), 
        						 Util.convertToString(camp.getRegulamento()), Util.convertToString(camp.getImagemCampeonato()), 
        						 camp.getFormato(), camp.getModalidade());
    }
		
	public List<CampeonatoDTO> findAll() {
		return campeonatoRepository.findAll()
				.stream()
				.map(c -> {
					try {
						return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), 
												 Util.convertToString(c.getRegulamento()), Util.convertToString(c.getImagemCampeonato()), c.getFormato(), c.getModalidade());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), null, null, c.getFormato(), c.getModalidade());
				}) 
				.collect(Collectors.toList());
	}
	
	public CampeonatoDTO findById(Long id) {
		Optional<Campeonato> campeonato = campeonatoRepository.findById(id);
		if (campeonato.isPresent()) {
			Campeonato c = campeonato.get();
			try {
				return converteDados(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public List<CampeonatoDTO> findByUsuarioIdAndModalidadeId(Long usuarioId, Long modalidadeId) {
		return campeonatoRepository.findByUsuariosIdAndModalidadeId(usuarioId, modalidadeId)
				.stream()
                .map(c -> {
                    try {
                        return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), 
                                                 Util.convertToString(c.getRegulamento()), Util.convertToString(c.getImagemCampeonato()), c.getFormato(), c.getModalidade());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), null, null, c.getFormato(), c.getModalidade());
                }) 
                .collect(Collectors.toList());
    }
	
	@Transactional
	public void create(CampeonatoDTO campeonatoDTO, Long idUsuario) {
		Campeonato campeonato = campeonatoRepository.save(new Campeonato(campeonatoDTO));
		CampeonatoUsuario campeonatoUsuario = new CampeonatoUsuario();
		campeonatoUsuario.setIdCampeonato(campeonato.getId());
		campeonatoUsuario.setIdCriador(idUsuario);
		campeonatoUsuarioService.create(campeonatoUsuario);
	}
	
	@Transactional
	public void adicionarEquipe(String cnpjAtletica, Long idCampeonato, Long idModalidade) {
		Usuario usuario = this.usuarioService.findByCnpj(cnpjAtletica);
		EquipeDTO equipe =  this.equipeService.findByAtleticaAndModalidade(usuario.getId(), idModalidade);
		if(equipe == null) {
			throw new RuntimeErrorException(null, "A atlética não possui um time da modalidade do campeonato!");
		}
		
		CampeonatoUsuario campeonatoUsuarioAux = this.campeonatoUsuarioService.findByIdCampeonatoAndIdAtletica(idCampeonato, usuario.getId());
		if(campeonatoUsuarioAux != null) {
			throw new RuntimeErrorException(null, "Equipe já adicionada.");
		}
		
		CampeonatoUsuario campeonatoUsuario = new CampeonatoUsuario(idCampeonato, usuario.getId());
		this.campeonatoUsuarioService.create(campeonatoUsuario);
	}
	
	@Transactional
	public void removerEquipe(Long idCampeonato, Long idEquipe) {
		Usuario atletica = this.usuarioService.findByEquipe(idEquipe);
		CampeonatoUsuario campeonatoUsuarioAux = this.campeonatoUsuarioService.findByIdCampeonatoAndIdAtletica(idCampeonato, atletica.getId());
		campeonatoUsuarioService.delete(campeonatoUsuarioAux.getId());
	}
	
	@Transactional
	public void update(CampeonatoDTO campeonatoDTO) {
		Campeonato novoCamp = new Campeonato(findById(campeonatoDTO.id()));
		novoCamp.setNome(campeonatoDTO.nome());
		novoCamp.setDataInicio(campeonatoDTO.dataInicio());
		novoCamp.setDataFim(campeonatoDTO.dataFim());
		novoCamp.setQuantidadeEquipes(campeonatoDTO.quantidadeEquipes());
		this.campeonatoRepository.save(novoCamp);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.campeonatoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}

	public List<CampeonatoDTO> findAllIntByModalidadeId(Long equipeId, Long modalidadeId) {
		Long usuarioId = membroRepository.findIdAtleticaByIdEquipe(equipeId);

		return campeonatoRepository.findAllIntByModalidadeId(usuarioId, modalidadeId)
				.stream()
				.map(c -> {
					try {
						return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(),
								Util.convertToString(c.getRegulamento()), Util.convertToString(c.getImagemCampeonato()), c.getFormato(), c.getModalidade());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), null, null, c.getFormato(), c.getModalidade());
				})
				.collect(Collectors.toList());
	}

	public List<CampeonatoDTO> findAllExtByModalidadeId(Long equipeId, Long modalidadeId) {
		Long usuarioId = membroRepository.findIdAtleticaByIdEquipe(equipeId);

		return campeonatoRepository.findAllExtByModalidadeId(usuarioId, modalidadeId)
				.stream()
				.map(c -> {
					try {
						return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(),
								Util.convertToString(c.getRegulamento()), Util.convertToString(c.getImagemCampeonato()), c.getFormato(), c.getModalidade());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), null, null, c.getFormato(), c.getModalidade());
				})
				.collect(Collectors.toList());
	}

	/*
	 * Método para iniciar o Campeonato e gerar as partidas
	 */
    @Transactional
    public void iniciarCampeonato(Long campeonatoId, Endereco endereco ) throws Exception {
        Campeonato campeonato = campeonatoRepository.findById(campeonatoId).orElseThrow(() -> new Exception("Campeonato não encontrado"));
        List<Equipe> equipes = equipeRepository.findByCampeonatoId(campeonatoId);
        
        //Verifica se a qtd de equipes mínima que é exigida de acordo com o formato do campeonato
        if (equipes.size() != campeonato.getQuantidadeEquipes() || equipes.isEmpty()) {
            throw new IllegalStateException("Número de equipes cadastradas não corresponde ao número esperado.");
        }
        
        int rodada = 1;
        
     // Criando a etapa do campeonato
        EtapaCampeonato etapa = new EtapaCampeonato();
        if (campeonato.getFormato().getId() == 1) {//PONTOS CORRIDOS            	
        	etapa.setNomeEtapa(campeonato.getFormato().getNome());
        	
        	etapa.setCampeonato(campeonato);
            etapaCampeonatoService.create(etapa);
            partidaService.gerarPartidasPontosCorridos(campeonato, etapa, equipes, endereco, rodada);
            
        } else if(campeonato.getFormato().getId() == 2) {//ELIMINATORIA SIMPLES
        	etapa.setNomeEtapa(campeonato.getFormato().getNome());
        	
        	int totalRodadas = this.totalRodadasEliminatoriaSimples(campeonato.getQuantidadeEquipes());
        	etapa.setTotalRodadas(totalRodadas);
        	etapa = etapaCampeonatoService.create(etapa);
        	
        	partidaService.gerarPartidasEliminatoriaSimples(endereco, campeonato, rodada, etapa);
        	
        }
    }
    
    public Map<Integer, List<PartidaChaveamento>> visualizarChaveamento(Long idCampeonato) {
    	List<EtapaCampeonatoDTO> etapasCampeonato = this.etapaCampeonatoService.findByCampeonato(idCampeonato);
    	EtapaCampeonato etapaCampeonato = new EtapaCampeonato(etapasCampeonato.get(0));

    	Map<Integer, List<PartidaChaveamento>> partidasPorRodada = new HashMap<>();
    	
    	for(int i = 1; i <= etapaCampeonato.getTotalRodadas(); i++) {
    		List<DadosPartida> dadosPartida = this.dadosPartidaService.findAllByRodadaCampeonato(i, etapaCampeonato.getId());
    		int rodada = i;
    		
    		for(DadosPartida dp : dadosPartida) {
    			
    			PartidaChaveamento partida = new PartidaChaveamento();
    			partida.setPartida(dp.getPartida().getId());
    			partida.setEquipeUm(dp.getPartida().getDadosPartidas().get(0).getEquipe());
    			partida.setEquipeDois(dp.getPartida().getDadosPartidas().get(1).getEquipe());
    			
    			// Verifique se a rodada já está no map
                if (!partidasPorRodada.containsKey(rodada)) {
                    // Se não estiver, crie uma nova lista para essa rodada
                    partidasPorRodada.put(rodada, new ArrayList<>());
                }
    		    
                // Adiciona partida do chaveamento por rodada
                partidasPorRodada.get(rodada).add(partida);
    		}
    		
    		if(!dadosPartida.isEmpty()) {
    			List<PartidaChaveamento> aux = partidasPorRodada.get(rodada);
        		aux = aux.stream().distinct().toList();
        		partidasPorRodada.get(rodada).clear();
        		partidasPorRodada.get(rodada).addAll(aux);
    		}
    	}
    	return partidasPorRodada;
    }
    
    public int totalRodadasEliminatoriaSimples(int totalTimes) {
        int iteracoes = 0;
        while (totalTimes > 1) {
        	totalTimes /= 2;
            iteracoes++;
        }
        return iteracoes;
    }
	
}
