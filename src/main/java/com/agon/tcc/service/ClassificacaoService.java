package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.ClassificacaoDTO;
import com.agon.tcc.model.Classificacao;
import com.agon.tcc.repository.ClassificacaoRepository;

@Service
public class ClassificacaoService {
	
	@Autowired
	private ClassificacaoRepository classificacaoRepository;

	public List<ClassificacaoDTO> findAll() {
        return classificacaoRepository.findAll()
                .stream()
                .map(c -> new ClassificacaoDTO(c.getId(), c.getVitorias(), c.getEmpates(), c.getDerrotas(), c.getSaldoGols(), c.getPontos(), c.getRodada()))
                .collect(Collectors.toList());
    }

    public ClassificacaoDTO findById(Long id) {
        Optional<Classificacao> classificacao = classificacaoRepository.findById(id);
        if (classificacao.isPresent()) {
            Classificacao c = classificacao.get();
            return new ClassificacaoDTO(c.getId(), c.getVitorias(), c.getEmpates(), c.getDerrotas(), c.getSaldoGols(), c.getPontos(), c.getRodada());
        }
        return null;
    }

//	public List<ClassificacaoDTO> findByEquipe(Long id) {
//		return classificacaoRepository.findByEquipe(id)
//				.stream()
//				.map(c -> new ClassificacaoDTO(c.getId(), c.getVitorias(), c.getEmpates(), c.getDerrotas(), c.getSaldoGols(), c.getPontos(), c.getRodada()))
//				.collect(Collectors.toList());
//	}
//
//	public List<ClassificacaoDTO> findByPartida(Long id) {
//		return classificacaoRepository.findByPartida(id)
//				.stream()
//				.map(dpp -> new ClassificacaoDTO(dpp.getId(), dpp.getPlacar(), dpp.getQtdeCartaoVermelho(), dpp.getQtdeCartaoAmarelo(), 
//												dpp.getPenaltis(), dpp.getEquipe().getId(), dpp.getPartida().getId()))
//				.collect(Collectors.toList());
//	}
//	
//	public ClassificacaoDTO findByEquipePartida(Long equipeId, Long partidaId) {
//        Optional<Classificacao> classificacao = classificacaoRepository.findByEquipePartida(equipeId, partidaId);
//        if (classificacao.isPresent()) {
//            Classificacao c = classificacao.get();
//            return new ClassificacaoDTO(c.getId(), c.getVitorias(), c.getEmpates(), c.getDerrotas(), c.getSaldoGols(), c.getPontos(), c.getRodada());
//        }
//        return null;
//    }

//    @Transactional
//    public void update(ClassificacaoDTO classificacaoDTO, Long equipeId, Long partidaId) {
//        Optional<Classificacao> optPartida = classificacaoRepository.findByEquipePartida(equipeId, partidaId);
//        if (optPartida.isPresent()) {
//            Classificacao classificacao = optPartida.get();
//            classificacao.setPlacar(classificacaoDTO.placar());
//            classificacao.setQtdeCartaoVermelho(classificacaoDTO.qtdeCartaoVermelho());
//            classificacao.setQtdeCartaoAmarelo(classificacaoDTO.qtdeCartaoAmarelo());
//            classificacao.setPenaltis(classificacaoDTO.penaltis());
//            
//            try {
//            	classificacaoRepository.save(classificacao);
//            } catch (Exception e) {
//				throw new RuntimeErrorException(null, "Não foi possível atualizar os dados da equipe " + classificacao.getEquipe().getNome() + 
//											" na partida " + classificacao.getPartida().getId());
//			}
//        }
//    }
	
	@Transactional
	public void create(ClassificacaoDTO classificacaoDTO) {
		classificacaoRepository.save(new Classificacao(classificacaoDTO));
	}
		
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.classificacaoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível excluir pois há entidades relacionadas!");
			}
		}
	}
}
