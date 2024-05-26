package com.agon.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

//	@Query("SELECT p FROM Partida p WHERE p.campeonato.id = :campeonatoId")
//    List<Partida> findByCampeonato(@Param("campeonatoId") Long campeonatoId);
}
