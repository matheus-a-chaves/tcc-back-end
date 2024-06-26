package com.agon.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agon.tcc.repository.LoginRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	@Autowired
	LoginRepository loginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return loginRepository.findByLogin(username);
	}

}
