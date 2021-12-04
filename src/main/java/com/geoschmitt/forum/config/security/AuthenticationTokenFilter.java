package com.geoschmitt.forum.config.security;

import com.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
import com.geoschmitt.forum.model.Usuario;
import com.geoschmitt.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationTokenFilter extends OncePerRequestFilter {


    private TokenService tokenService;
    private UserRepository userRepository;

    public AuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository){
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = tokenRecover(request);
        if(tokenService.isValidToken(token))
            this.userAuthenticate(token);

        filterChain.doFilter(request, response);
    }

    public String tokenRecover(HttpServletRequest req){
        String token = req.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer "))
            return null;
        return token.substring(7, token.length());
    }

    private void userAuthenticate(String token){
        Optional<Usuario> usuario = userRepository.findById(tokenService.getIdUsuario(token));
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.get(), null, usuario.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
