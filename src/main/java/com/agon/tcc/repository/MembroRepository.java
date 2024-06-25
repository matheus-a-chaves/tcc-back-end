package com.agon.tcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agon.tcc.model.Membro;
import org.springframework.data.jpa.repository.Query;

public interface MembroRepository extends JpaRepository<Membro, Long>{
	
	//@Query(value = "SELECT m.* FROM Membros m WHERE m.id_equipe = :idEquipe AND m.id_atletica = :idAtletica AND m.id_jogador = :idJogador", nativeQuery  = true)
	Optional<Membro> findByIdEquipeAndIdAtleticaAndIdJogador(Long idEquipe, Long idAtletica, Long idJogador);

	@Query(value = "select id_atletica from membros m where m.id_equipe = :idEquipe", nativeQuery = true)
	Long findIdAtleticaByIdEquipe(Long idEquipe);

	Optional<Membro>  findMembroByIdJogador(Long idJogador);
}
