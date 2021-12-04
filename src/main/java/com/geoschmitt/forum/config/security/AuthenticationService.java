package com.geoschmitt.forum.config.security;

import com.geoschmitt.forum.model.Usuario;
import com.geoschmitt.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = userRepository.findByEmail(username);
        if(usuario.isPresent())
            return usuario.get();
        throw new UsernameNotFoundException("Invalid User");
    }
}
