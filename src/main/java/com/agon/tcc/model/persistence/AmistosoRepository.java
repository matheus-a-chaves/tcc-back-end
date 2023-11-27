package com.agon.tcc.model.persistence;

import com.agon.tcc.model.entity.Amistoso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmistosoRepository extends JpaRepository<Amistoso, Long>  {
    @Query(value = "SELECT * FROM amistoso WHERE fk_equipe_desafiante IN (?1) " +
            "OR amistoso.fk_equipe_convidada IN (?1) " +
            "AND status = ?2 AND fk_modalidade = ?3 ", nativeQuery = true)
    List<Amistoso> findAllIdUser(List<Long> id, String status, Long idModalidade);

    @Query(value = "SELECT * FROM amistoso WHERE amistoso.fk_equipe_convidada IN (?1) AND status = ?2",
            nativeQuery = true)
    List<Amistoso> findAllNotifications(List<Long>id, String status);

    @Modifying
    @Query(value = "UPDATE amistoso SET status=?2 WHERE id = ?1",
            nativeQuery = true)
    void updateAmistoso(Long id, String status);
}
