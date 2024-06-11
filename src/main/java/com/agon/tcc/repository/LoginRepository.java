package com.agon.tcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.agon.tcc.model.Login;


public interface LoginRepository extends JpaRepository<Login, Long>{
	
	UserDetails findByLogin(String login);
	
	@Query(value = "SELECT log.* FROM Login log WHERE log.login = :login", nativeQuery  = true)
	Optional<Login> findLogin(String login);

}
