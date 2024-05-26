package com.agon.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

//	@Query("SELECT e FROM Equipe e JOIN e.partidas p WHERE p.campeonato.id = :campeonatoId")
//    List<Equipe> findByCampeonatoId(Long campeonatoId);

}
