package com.agon.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long>{
	
	@Query(value = "SELECT e.* FROM Equipe e JOIN Campeonato_Equipe ce ON e.id = ce.equipe_id WHERE ce.campeonato_id = :campeonatoId", nativeQuery = true)
    List<Equipe> findAllEquipesByIdCampeonato(Long campeonatoId);
}