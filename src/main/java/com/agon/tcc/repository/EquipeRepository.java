package com.agon.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

	@Query("SELECT DISTINCT e FROM Equipe e JOIN e.dadosPartidas dp JOIN dp.partida p JOIN p.etapaCampeonato ec WHERE ec.campeonato.id = :campeonatoId")
    List<Equipe> findByCampeonatoId(@Param("campeonatoId") Long campeonatoId);

}
