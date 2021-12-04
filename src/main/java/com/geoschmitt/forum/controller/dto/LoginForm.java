package com.geoschmitt.forum.controller.dto;

import com.geoschmitt.forum.model.Curso;
import com.geoschmitt.forum.model.Topico;
import com.geoschmitt.forum.repository.CursoRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
    private String email;
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken convert(){
        return new UsernamePasswordAuthenticationToken(this.email, this.senha);
    }
}
