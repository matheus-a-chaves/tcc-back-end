package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.GrupoDTO;
import com.agon.tcc.model.Grupo;
import com.agon.tcc.repository.GrupoRepository;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
		
	public List<GrupoDTO> findAll() {
        return grupoRepository.findAll()
                .stream()
                .map(g -> new GrupoDTO(g.getId(), g.getNome(), g.getTotalJogos(), g.getCampeonato(), g.getEquipesGrupos(), g.getPartidas()))
                .collect(Collectors.toList());
    }

    public GrupoDTO findById(Long id) {
        Optional<Grupo> grupo = grupoRepository.findById(id);
        if (grupo.isPresent()) {
            Grupo g = grupo.get();
            return new GrupoDTO(g.getId(), g.getNome(), g.getTotalJogos(), g.getCampeonato(), g.getEquipesGrupos(), g.getPartidas());
        }
        return null;
    }

    @Transactional
    public void update(GrupoDTO grupoDTO) {
        Optional<Grupo> optGrupo = grupoRepository.findById(grupoDTO.id());
        if (optGrupo.isPresent()) {
            Grupo grupo = optGrupo.get();
            grupo.setTotalJogos(grupoDTO.totalJogos());
            grupo.setPartidas(grupoDTO.partidas());
            
            try {
            	grupoRepository.save(grupo);
            } catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível atualizar a grupo " + grupo.getId());
			}
        }
    }
	
	@Transactional
	public void create(GrupoDTO grupoDTO/*, List<Long> idsEquipes*/) {
//	    Grupo grupo = new Grupo();
//	    // Recuperar as equipes do banco de dados com base nos IDs fornecidos
//	    List<Equipe> equipesGrupo = equipeRepository.findAllById(idsEquipes);
//	    grupo.setEquipes(equipesGrupo);
	    
	    try {
		    grupoRepository.save(new Grupo(grupoDTO));
        } catch (Exception e) {
			throw new RuntimeErrorException(null, "Não foi possível salvar a grupo " + grupoDTO.id());
		}
	}
		
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.grupoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível excluir pois há entidades relacionadas!");
			}
		}
	}

//    public String obterGrupoEmJson(Long idGrupo) {
//        Grupo grupo = grupoRepository.findById(idGrupo).orElseThrow(() -> new GrupoNaoEncontradoException(idGrupo));
//
//        // Transferindo a lógica de cálculo para a service
//        calcularIndicadores(grupo);
//
//        // ... (restante do código de mapeamento para JSON omitido)
//    }

//    private void calcularIndicadores(Grupo grupo) {
//        grupo.setPontosTotal(0);
//        grupo.setJogosTotal(0);
//        grupo.setVitoriasTotal(0);
//        grupo.setEmpatesTotal(0);
//        grupo.setDerrotasTotal(0);
//        grupo.setSaldoDeGolsTotal(0);
//
//        for (Equipe equipe : grupo.getEquipes()) {
//            grupo.setPontosTotal(grupo.getPontosTotal() + equipe.getPontos());
//            grupo.setJogosTotal(grupo.getJogosTotal() + equipe.getJogos());
//            grupo.setVitoriasTotal(grupo.getVitoriasTotal() + equipe.getVitorias());
//            grupo.setEmpatesTotal(grupo.getEmpatesTotal() + equipe.getEmpates());
//            grupo.setDerrotasTotal(grupo.getDerrotasTotal() + equipe.getDerrotas());
//            grupo.setSaldoDeGolsTotal(grupo.getSaldoDeGolsTotal() + equipe.getSaldoDeGols());
//        }
//
//        // Salvar as alterações no grupo
//        grupoRepository.save(grupo);
//    }

}
