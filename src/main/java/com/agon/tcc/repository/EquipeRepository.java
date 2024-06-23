package com.agon.tcc.repository;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

	@Query("SELECT DISTINCT e FROM Equipe e JOIN e.dadosPartidas dp JOIN dp.partida p JOIN p.etapaCampeonato ec WHERE ec.campeonato.id = :campeonatoId")
    List<Equipe> findByCampeonatoId(@Param("campeonatoId") Long campeonatoId);

	@Query(value = "SELECT DISTINCT e2.*\n" +
			"FROM membros m\n" +
			"JOIN equipe e2 ON e2.id = m.id_equipe\n" +
			"WHERE m.id_atletica IN (\n" +
			"    SELECT c.usuario_id\n" +
			"    FROM campeonato_usuario c\n" +
			"    WHERE c.campeonato_id = :idCampeonato\n" +
			")\n" +
			"AND e2.codigo_modalidade IN (\n" +
			"    SELECT c2.codigo_modalidade\n" +
			"    FROM campeonato c2\n" +
			"    WHERE c2.id = :idCampeonato\n" +
			")\n", nativeQuery  = true)
	List<Equipe> findAllTimesByIdCampeonato(Long idCampeonato);

	@Query(value = "SELECT DISTINCT e.* FROM Equipe e JOIN Membros m ON e.id = m.id_equipe WHERE m.id_atletica = :idAtletica", nativeQuery  = true)
	List<Equipe> findAllTimesByIdAtletica(Long idAtletica);

	@Query(value = "SELECT e.* FROM Equipe e JOIN Membros m ON e.id = m.id_equipe WHERE m.id_jogador = :idJogador", nativeQuery  = true)
	List<Equipe> findAllTimesByIdJogador(Long idJogador);
	
	@Query(value = "SELECT e.* FROM Equipe e JOIN Membros m ON e.id = m.id_equipe WHERE m.id_atletica = :idAtletica AND e.codigo_modalidade = :idModalidade", nativeQuery  = true)
	Optional<Equipe> findByAtleticaAndModalidade(Long idAtletica, Long idModalidade);
	
	@Query(value = "SELECT DISTINCT e.* FROM Equipe e JOIN Membros m ON e.id = m.id_equipe "
			+ "WHERE e.codigo_modalidade = :idModalidade "
			+ "AND m.id_atletica != :idAtletica "
			+ "AND e.id not in (SELECT e.id FROM equipe e JOIN dados_partida dp ON e.id = dp.equipe_id JOIN partida p ON dp.partida_id = p.id WHERE date(p.data_partida) = :data)", nativeQuery  = true)
	List<Equipe> findTimesDisponiveisAmistoso(@Param("data") LocalDate data, Long idModalidade, Long idAtletica);

	Optional<Equipe> findByNome(String nome);

	@Modifying
	@Query(value = "INSERT INTO Membros(id_equipe, id_atletica) values (:idEquipe,:idAtletica)", nativeQuery  = true)
	void saveMembros(Long idEquipe, Long idAtletica);

}