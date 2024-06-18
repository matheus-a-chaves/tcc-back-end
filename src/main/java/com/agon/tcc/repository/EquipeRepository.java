package com.agon.tcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

	@Query(value = "SELECT e.* FROM Equipe e JOIN Equipe_Grupo eg ON e.id = eg.equipe_id JOIN grupo g ON eg.grupo_id = g.id JOIN campeonato c ON g.campeonato_id = c.id WHERE c.id = :idCampeonato", nativeQuery  = true)
	List<Equipe> findAllTimesByIdCampeonato(Long idCampeonato);
	
	@Query(value = "SELECT DISTINCT e.* FROM Equipe e JOIN Membros m ON e.id = m.id_equipe WHERE m.id_atletica = :idAtletica", nativeQuery  = true)
	List<Equipe> findAllTimesByIdAtletica(Long idAtletica);
	
	@Query(value = "SELECT e.* FROM Equipe e JOIN Membros m ON e.id = m.id_equipe WHERE m.id_jogador = :idJogador", nativeQuery  = true)
	List<Equipe> findAllTimesByIdJogador(Long idJogador);

	Optional<Equipe> findByNome(String nome);
	
	@Modifying
	@Query(value = "INSERT INTO Membros(id_equipe, id_atletica) values (:idEquipe,:idAtletica)", nativeQuery  = true)
	void saveMembros(Long idEquipe, Long idAtletica);

}
