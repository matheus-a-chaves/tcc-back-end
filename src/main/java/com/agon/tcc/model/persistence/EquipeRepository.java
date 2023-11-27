package com.agon.tcc.model.persistence;

import com.agon.tcc.model.entity.Amistoso;
import com.agon.tcc.model.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    @Query(value = " SELECT * FROM equipe e where fk_usuario = ?1", nativeQuery = true)
    List<Equipe> findAllByIdUser(Long idUsuario);

    @Query(value = "SELECT * FROM equipe e where fk_usuario = ?1 and fk_modalidade = ?2",
            nativeQuery = true)
    List<Equipe> findAllByIdModal(Long idUsuario, Long idModalidade);

}
