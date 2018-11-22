package com.victor.cursospring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.victor.cursospring.domain.Cliente;
import com.victor.cursospring.repositories.ClienteRepository;
import com.victor.cursospring.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private ClienteRepository clienteRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cli = clienteRepo.findByEmail(email);
        
        if(cli == null) {
            throw new UsernameNotFoundException(email);
        }
        
        return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
    }

}
