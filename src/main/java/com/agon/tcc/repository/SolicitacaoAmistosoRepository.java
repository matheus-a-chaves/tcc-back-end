package com.agon.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.agon.tcc.model.SolicitacaoAmistoso;

public interface SolicitacaoAmistosoRepository extends JpaRepository<SolicitacaoAmistoso, Long> {

	@Query(value = "SELECT sa.* FROM solicitacao_amistoso sa \n" +
			"WHERE sa.status = 'PENDENTE' AND sa.equipe_visitante_id \n" +
			"in (SELECT DISTINCT m.id_equipe FROM membros m WHERE m.id_atletica=:atleticaId)", nativeQuery = true)
	List<SolicitacaoAmistoso> buscarSolicitacoesAtletica(Long atleticaId);
}
