package com.agon.tcc.model.persistence;

import com.agon.tcc.model.entity.Amistoso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmistosoRepository extends JpaRepository<Amistoso, Integer>  {
}
