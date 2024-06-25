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
    private ResultadoService resultadoService;
    
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
            throw new IllegalStateException("Número de equipes cadastradas não corresponde ao número esperado.");
        }
        
        // Criando a etapa do campeonato
        EtapaCampeonato etapa = new EtapaCampeonato();
        if (campeonato.getFormato().getId() == 1) {//PONTOS CORRIDOS            	
        	etapa.setNomeEtapa(campeonato.getFormato().getNome());
        }
        etapa.setCampeonato(campeonato);
        etapaCampeonatoService.create(etapa);
        int rodada = 1;
        partidaService.gerarPartidasPontosCorridos(campeonato, etapa, equipes, endereco, rodada);        
    }
    
    @Transactional
    public void gerarPartidasEliminatoriaSimples(Endereco endereco, Long idCampeonato, Integer idRodada) throws Exception {
    	try {
    		Campeonato campeonato = campeonatoRepository.findById(idCampeonato).orElseThrow(() -> new Exception("Campeonato não encontrado"));
    		
    		if(campeonato.getQuantidadeEquipes() != campeonato.getUsuarios().size()) {
        		throw new RuntimeErrorException(null, "A quantidade de times inscritos é diferente do previsto para o campeonato!");
        	}
        	
        	if(idRodada == 1) {
        		
        		if(endereco == null) {
            		throw new RuntimeErrorException(null, "Favor cadastrar um endereço para o campeonato!");
            	}
        		
        		int totalRodadas = this.totalRodadasEliminatoriaSimples(campeonato.getQuantidadeEquipes());
        		EtapaCampeonato etapaCampeonato = new EtapaCampeonato(campeonato.getFormato().getNome(), campeonato, totalRodadas);
            	etapaCampeonato = etapaCampeonatoService.create(etapaCampeonato);
        		
        		this.gerarPrimeiraRodada(campeonato, idCampeonato, etapaCampeonato, endereco);
        	} else {
        		this.gerarProximaRodada(campeonato, idCampeonato, idRodada);
        	}
        	
    	} catch(Exception ex) {
    		throw new RuntimeErrorException(null, ex.getMessage());
    	}
    }
    
    public void gerarProximaRodada(Campeonato campeonato, Long idCampeonato, Integer idRodada) {
    	
    	List<EtapaCampeonatoDTO> etapasCampeonato = this.etapaCampeonatoService.findByCampeonato(idCampeonato);
    	
    	Integer rodadaAtual = idRodada;
    	EtapaCampeonato etapaCampeonato = new EtapaCampeonato(etapasCampeonato.get(0));
    	
    	if(idRodada > etapaCampeonato.getTotalRodadas()) {
    		throw new RuntimeErrorException(null, "O campeonato não possui mais rodadas!");
    	}
    	
    	List<DadosPartida> dadosPartida = this.dadosPartidaService.findAllByRodadaCampeonato(idRodada - 1, etapaCampeonato.getId());
    	int contDadosAtualizados = 0;
    	
    	for(DadosPartida dp : dadosPartida) {
    		if(dp.isDadosAtualizados()) {
    			contDadosAtualizados++;
    		}
    	}
    	
    	Endereco endereco = new Endereco();
    	endereco = dadosPartida.get(0).getPartida().getEndereco();
    	
    	if(contDadosAtualizados != dadosPartida.size()) {
    		throw new RuntimeErrorException(null, "Favor preencher todos os resultados das partidas da rodada " + (idRodada-1));
    	} else {
    		List<Equipe> equipeVencedorasRodadaAnterior =  this.equipeService.findEquipesVencedorasRodadaAnterior(idRodada-1);
    		
    		int qtdadePartidas = equipeVencedorasRodadaAnterior.size()/2;
    		List<Equipe> grupoUm = equipeVencedorasRodadaAnterior.subList(0, qtdadePartidas);
        	List<Equipe> grupoDois = equipeVencedorasRodadaAnterior.subList(qtdadePartidas, equipeVencedorasRodadaAnterior.size());
        	
        	for(int i = 0; i < qtdadePartidas; i++) {
        		
        		Partida partida = new Partida();
                partida.setDataPartida(LocalDateTime.now().plusDays(3));
                partida.setEtapaCampeonato(etapaCampeonato);
                partida.setEndereco(endereco);
                partida.setCampeonato(campeonato);
                
                DadosPartida dadosPartida1 = new DadosPartida();
                dadosPartida1.setEquipe(grupoUm.get(i));
                dadosPartida1.setPartida(partida);
                dadosPartida1.setPlacar(0);
                dadosPartida1.setQtdeCartaoAmarelo(0);
                dadosPartida1.setQtdeCartaoVermelho(0);
                dadosPartida1.setPenaltis(0);
                dadosPartida1.setDadosAtualizados(false);
                
                DadosPartida dadosPartida2 = new DadosPartida();
                dadosPartida2.setEquipe(grupoDois.get(i));
                dadosPartida2.setPartida(partida);
                dadosPartida2.setPlacar(0);
                dadosPartida2.setQtdeCartaoAmarelo(0);
                dadosPartida2.setQtdeCartaoVermelho(0);
                dadosPartida2.setPenaltis(0);
                dadosPartida2.setDadosAtualizados(false);
                
        		partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
        		partidaService.create(partida);
        		
        		Resultado resultadoEquipe1 = new Resultado();
        		resultadoEquipe1.setDadosPartida(dadosPartida1);
        		resultadoEquipe1.setRodada(rodadaAtual);
        		resultadoEquipe1.setVitorias(0);
        		resultadoEquipe1.setEmpates(0);
        		resultadoEquipe1.setDerrotas(0);
        		resultadoEquipe1.setSaldoGols("0");
        		resultadoEquipe1.setPontos(0);
        		resultadoEquipe1.setEtapaCampeonato(etapaCampeonato);
        		resultadoService.create(resultadoEquipe1);
        		
        		Resultado resultadoEquipe2 = new Resultado();
        		resultadoEquipe2.setDadosPartida(dadosPartida2);
        		resultadoEquipe2.setRodada(rodadaAtual);
        		resultadoEquipe2.setVitorias(0);
        		resultadoEquipe2.setEmpates(0);
        		resultadoEquipe2.setDerrotas(0);
        		resultadoEquipe2.setSaldoGols("0");
        		resultadoEquipe2.setPontos(0);
        		resultadoEquipe2.setEtapaCampeonato(etapaCampeonato);
        		resultadoService.create(resultadoEquipe2);
        	}
    	}
    }
    
    public void gerarPrimeiraRodada(Campeonato campeonato, Long idCampeonato, EtapaCampeonato etapaCampeonato, Endereco endereco) {
    	int qtdadePartidasIniciais = campeonato.getQuantidadeEquipes()/2;
    	
    	List<Equipe> equipesParticipantes = this.equipeRepository.findAllTimesByIdCampeonato(idCampeonato);
    	List<Equipe> grupoUm = equipesParticipantes.subList(0, qtdadePartidasIniciais);
    	List<Equipe> grupoDois = equipesParticipantes.subList(qtdadePartidasIniciais, equipesParticipantes.size());
    	
    	for(int i = 0; i < qtdadePartidasIniciais; i++) {
    		
    		Partida partida = new Partida();
            partida.setDataPartida(LocalDateTime.now().plusDays(3));
            partida.setEtapaCampeonato(etapaCampeonato);
            partida.setEndereco(endereco);
            partida.setCampeonato(campeonato);
            
            DadosPartida dadosPartida1 = new DadosPartida();
            dadosPartida1.setEquipe(grupoUm.get(i));
            dadosPartida1.setPartida(partida);
            dadosPartida1.setPlacar(0);
            dadosPartida1.setQtdeCartaoAmarelo(0);
            dadosPartida1.setQtdeCartaoVermelho(0);
            dadosPartida1.setPenaltis(0);
            dadosPartida1.setDadosAtualizados(false);
            
            DadosPartida dadosPartida2 = new DadosPartida();
            dadosPartida2.setEquipe(grupoDois.get(i));
            dadosPartida2.setPartida(partida);
            dadosPartida2.setPlacar(0);
            dadosPartida2.setQtdeCartaoAmarelo(0);
            dadosPartida2.setQtdeCartaoVermelho(0);
            dadosPartida2.setPenaltis(0);
            dadosPartida2.setDadosAtualizados(false);
            
    		partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
    		partidaService.create(partida);
    		
    		Resultado resultadoEquipe1 = new Resultado();
    		resultadoEquipe1.setDadosPartida(dadosPartida1);
    		resultadoEquipe1.setRodada(1);
    		resultadoEquipe1.setVitorias(0);
    		resultadoEquipe1.setEmpates(0);
    		resultadoEquipe1.setDerrotas(0);
    		resultadoEquipe1.setSaldoGols("0");
    		resultadoEquipe1.setPontos(0);
    		resultadoEquipe1.setEtapaCampeonato(etapaCampeonato);
    		resultadoService.create(resultadoEquipe1);
    		
    		Resultado resultadoEquipe2 = new Resultado();
    		resultadoEquipe2.setDadosPartida(dadosPartida2);
    		resultadoEquipe2.setRodada(1);
    		resultadoEquipe2.setVitorias(0);
    		resultadoEquipe2.setEmpates(0);
    		resultadoEquipe2.setDerrotas(0);
    		resultadoEquipe2.setSaldoGols("0");
    		resultadoEquipe2.setPontos(0);
    		resultadoEquipe2.setEtapaCampeonato(etapaCampeonato);
    		resultadoService.create(resultadoEquipe2);
    	}
    }
    
    public int totalRodadasEliminatoriaSimples(int totalTimes) {
        int iteracoes = 0;
        while (totalTimes > 1) {
        	totalTimes /= 2;
            iteracoes++;
        }
        return iteracoes;
    }
    
    public Map<Integer, List<PartidaChaveamento>> visualizarChaveamento(Long idCampeonato) {
    	List<EtapaCampeonatoDTO> etapasCampeonato = this.etapaCampeonatoService.findByCampeonato(idCampeonato);
    	EtapaCampeonato etapaCampeonato = new EtapaCampeonato(etapasCampeonato.get(0));

    	Map<Integer, List<PartidaChaveamento>> partidasPorRodada = new HashMap<>();
    	
    	for(int i = 1; i <= etapaCampeonato.getTotalRodadas(); i++) {
    		List<DadosPartida> dadosPartida = this.dadosPartidaService.findAllByRodadaCampeonato(i, etapaCampeonato.getId());
    		
    		for(DadosPartida dp : dadosPartida) {
    			int rodada = i;
    			
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
    	}
    	return partidasPorRodada;
    }
	
}
