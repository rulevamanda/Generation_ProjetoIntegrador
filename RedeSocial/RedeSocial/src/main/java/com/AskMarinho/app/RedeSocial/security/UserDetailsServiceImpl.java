package com.AskMarinho.app.RedeSocial.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.AskMarinho.app.RedeSocial.models.User;
import com.AskMarinho.app.RedeSocial.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository repositoryU;
	
	@Override
	public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException{
		Optional<User> user = repositoryU.findByEmail(email);
		
		user.orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
		
		return user.map(UserDetailsImpl::new).get();
		
		
		
	}
	


}
