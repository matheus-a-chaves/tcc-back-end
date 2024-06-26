package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

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
import com.agon.tcc.model.Usuario;
import com.agon.tcc.repository.CampeonatoRepository;
import com.agon.tcc.repository.EquipeRepository;
import com.agon.tcc.repository.MembroRepository;
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
    public void iniciarCampeonato(Long idCampeonato, Endereco endereco) throws Exception {
    	Campeonato campeonato = campeonatoRepository.findById(idCampeonato).orElseThrow(() -> new Exception("Campeonato não encontrado"));
    	List<Equipe> equipes = equipeService.findAllEquipesByIdCampeonato(idCampeonato);
    	
        //Verifica se a qtd de equipes mínima que é exigida de acordo com o formato do campeonato
        if (equipes.size() != campeonato.getQuantidadeEquipes() || equipes.isEmpty()) {
            throw new RuntimeErrorException(null, "Número de equipes cadastradas não corresponde ao número esperado.");
        }
		if (endereco == null) {
			throw new RuntimeErrorException(null, "Favor cadastrar um endereço para o campeonato!");
		}
		
		EtapaCampeonato etapa = new EtapaCampeonato();
		etapa.setCampeonato(campeonato);
        
        if (campeonato.getFormato().getId() == 1) {//PONTOS CORRIDOS
    		etapa.setNomeEtapa(campeonato.getFormato().getNome());
            etapaCampeonatoService.create(etapa);
        	this.gerarPartidasPontosCorridos(campeonato, equipes, endereco, etapa);
        }
        if (campeonato.getFormato().getId() == 2) {//ELIMINATÓRIA SIMPLES
    		etapa.setNomeEtapa(campeonato.getFormato().getNome());
            etapaCampeonatoService.create(etapa);
            this.gerarPartidasEliminatoriaSimples(endereco, campeonato, 1, etapa);
        }
        if (campeonato.getFormato().getId() == 3) {//FASE DE GRUPOS + ELIMINATÓRIA SIMPLES
    		etapa.setNomeEtapa(campeonato.getFormato().getNome());
            etapaCampeonatoService.create(etapa);
//        	this.gerarPartidasFaseDeGrupos();
        }
        if (campeonato.getFormato().getId() == 4) {//PONTOS CORRIDOS + ELIMINATÓRIA SIMPLES
        	etapa.setNomeEtapa(campeonato.getFormato().getNome());
            etapaCampeonatoService.create(etapa);
        }
    }
    
    @Transactional
    public void gerarPartidasPontosCorridos(Campeonato campeonato, List<Equipe> equipes, Endereco endereco, EtapaCampeonato etapa) throws Exception {
    	try {
	        // Gerando todas as partidas de ida
	        for (int i = 0; i < equipes.size(); i++) {
	            for (int j = i + 1; j < equipes.size(); j++) {
	            	partidaService.gerarPartidaCampeonato(campeonato, equipes.get(i), equipes.get(j), endereco, etapa, 1);
	            }
	        }
    	} catch (Exception ex) {
    		throw new RuntimeErrorException(null, ex.getMessage());
    	}
    }
    
    @Transactional
    public void gerarPartidasEliminatoriaSimples(Endereco endereco, Campeonato campeonato, Integer idRodada, EtapaCampeonato etapaCampeonato) throws Exception {
		try {			
			if (idRodada == 1) {
				int totalRodadas = this.totalRodadas(campeonato.getQuantidadeEquipes());
				etapaCampeonato.setTotalRodadas(totalRodadas);
				etapaCampeonato = etapaCampeonatoService.update(etapaCampeonato);
				this.gerarPrimeiraRodada(campeonato, etapaCampeonato, endereco);
			} else {
				this.gerarProximaRodada(campeonato, idRodada);
			}
		} catch (Exception ex) {
			throw new RuntimeErrorException(null, ex.getMessage());
		}
    }
    
    public void gerarProximaRodada(Campeonato campeonato, Integer idRodada) {
    	List<EtapaCampeonatoDTO> etapasCampeonato = this.etapaCampeonatoService.findByCampeonato(campeonato.getId());
    	
    	Integer rodadaAtual = idRodada;
    	EtapaCampeonato etapaCampeonato = new EtapaCampeonato(etapasCampeonato.get(0));
    	
    	if (idRodada > etapaCampeonato.getTotalRodadas()) {
    		throw new RuntimeErrorException(null, "O campeonato não possui mais rodadas!");
    	}
    	
    	List<DadosPartida> dadosPartida = this.dadosPartidaService.findAllByRodadaCampeonato(idRodada - 1);
    	int contDadosAtualizados = 0;
    	
    	for (DadosPartida dp : dadosPartida) {
    		if (dp.isDadosAtualizados()) {
    			contDadosAtualizados++;
    		}
    	}
    	
    	Endereco endereco = new Endereco();
    	endereco = dadosPartida.get(0).getPartida().getEndereco();
    	
    	if (contDadosAtualizados != dadosPartida.size()) {
    		throw new RuntimeErrorException(null, "Favor preencher todos os resultados das partidas da rodada " + (idRodada-1));
    	} else {
    		List<Equipe> equipeVencedorasRodadaAnterior =  this.equipeService.findEquipesVencedorasRodadaAnterior(idRodada-1);
    		
    		int qtdadePartidas = equipeVencedorasRodadaAnterior.size()/2;
    		List<Equipe> grupoUm = equipeVencedorasRodadaAnterior.subList(0, qtdadePartidas);
        	List<Equipe> grupoDois = equipeVencedorasRodadaAnterior.subList(qtdadePartidas, equipeVencedorasRodadaAnterior.size());
        	
        	for (int i = 0; i < qtdadePartidas; i++) {
        		partidaService.gerarPartidaCampeonato(campeonato, grupoUm.get(i), grupoDois.get(i), endereco, etapaCampeonato, rodadaAtual);
        	}
    	}
    }
    
    public void gerarPrimeiraRodada(Campeonato campeonato, EtapaCampeonato etapaCampeonato, Endereco endereco) {
    	int qtdadePartidasIniciais = campeonato.getQuantidadeEquipes()/2;
    	
    	List<Equipe> equipesParticipantes = this.equipeRepository.findAllTimesByIdCampeonato(campeonato.getId());
    	List<Equipe> grupoUm = equipesParticipantes.subList(0, qtdadePartidasIniciais);
    	List<Equipe> grupoDois = equipesParticipantes.subList(qtdadePartidasIniciais, equipesParticipantes.size());
    	
    	for (int i = 0; i < qtdadePartidasIniciais; i++) {
    		partidaService.gerarPartidaCampeonato(campeonato, grupoUm.get(i), grupoDois.get(i), endereco, etapaCampeonato, 1);
    	}
    }
    
    public int totalRodadas(int totalTimes) {
        int iteracoes = 0;
        while (totalTimes > 1) {
        	totalTimes /= 2;
            iteracoes++;
        }
        return iteracoes;
    }
	
}
