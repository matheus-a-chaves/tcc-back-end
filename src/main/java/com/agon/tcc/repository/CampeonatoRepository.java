package com.agon.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {

	@Query(value = "SELECT DISTINCT c FROM Campeonato c JOIN c.usuarios u JOIN c.modalidade m WHERE u.id = :usuarioId AND m.id = :modalidadeId", nativeQuery = true)
    List<Campeonato> findByUsuariosIdAndModalidadeId(Long usuarioId, Long modalidadeId);

	@Query(value = "SELECT c.* FROM campeonato c inner join campeonato_usuario cu on C.id = CU.campeonato_id where cu.criador_id  = :usuarioId AND c.codigo_modalidade = :modalidadeId",  nativeQuery  = true)
	List<Campeonato> findAllIntByModalidadeId(Long usuarioId, Long modalidadeId);

    @Query(value = "SELECT c.* " +
    			   "  FROM campeonato c " + 
    			   " INNER JOIN campeonato_usuario cu ON c.id = cu.campeonato_id " +
				   " WHERE cu.usuario_id = :usuarioId " +
				   "   AND c.codigo_modalidade = :modalidadeId " +
				   "   AND c.id NOT IN (SELECT c2.id " +
				   "      				  FROM campeonato c2 " +
				   "      				 INNER JOIN campeonato_usuario cu2 ON c2.id = cu2.campeonato_id " +
				   "      				 WHERE cu2.criador_id = :usuarioId " +
				   "        			   AND c2.codigo_modalidade = :modalidadeId);", nativeQuery = true)
	List<Campeonato> findAllExtByModalidadeId(Long usuarioId, Long modalidadeId);

	Campeonato findByNome(String nome);
}
