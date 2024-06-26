package com.agon.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query(value = "SELECT u.* FROM Usuario u JOIN Membros m ON u.id = m.id_jogador WHERE m.id_equipe = :idEquipe", nativeQuery  = true)
	List<Usuario> findAllJogadoresByEquipe(Long idEquipe);

	@Query(value = "SELECT distinct u.* FROM usuario u \n" +
			"inner join membros m on m.id_atletica = u.id\n" +
			"where m.id_equipe =:idEquipe", nativeQuery = true)
	Usuario findByEquipe(Long idEquipe);
	
	Usuario findByCpf(String cpf);

	Usuario findByCnpj(String cnpj);
}
