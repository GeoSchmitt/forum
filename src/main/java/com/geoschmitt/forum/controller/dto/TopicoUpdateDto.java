package com.geoschmitt.forum.controller.dto;

import com.geoschmitt.forum.model.Topico;
import com.geoschmitt.forum.repository.TopicoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TopicoUpdateDto {

    @NotNull
    @NotEmpty
    @Size(min = 5)
    private String titulo;

    @NotNull @NotEmpty @Size(min = 10)
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico update(Long id, TopicoRepository topicoRepository){
        Topico topico = topicoRepository.getById(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
