package com.agon.tcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agon.tcc.model.EtapaCampeonato;

public interface EtapaCampeonatoRepository extends JpaRepository<EtapaCampeonato, Long> {

	@Query("SELECT ec FROM EtapaCampeonato ec WHERE ec.campeonato.id = :campeonatoId")
	List<EtapaCampeonato> findByCampeonato(@Param("campeonatoId") Long campeonatoId);

	@Query("SELECT ec FROM EtapaCampeonato ec WHERE ec.nomeEtapa = :nomeEtapa AND ec.campeonato.id = :campeonatoId")
	Optional<EtapaCampeonato> findByEtapaCampeonato(@Param("nomeEtapa")String nomeEtapa, @Param("campeonatoId") Long campeonatoId);

}
