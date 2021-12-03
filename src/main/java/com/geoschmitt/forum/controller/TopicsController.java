package com.geoschmitt.forum.controller;

import com.geoschmitt.forum.controller.dto.TopicoDto;
import com.geoschmitt.forum.controller.dto.TopicoForm;
import com.geoschmitt.forum.model.Curso;
import com.geoschmitt.forum.model.Topico;

import com.geoschmitt.forum.repository.CursoRepository;
import com.geoschmitt.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicsController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> list(String name){

        List<Topico> topicos;

        if (name == null)
            topicos = topicoRepository.findAll();
        else
            topicos = topicoRepository.findByCursoNome(name);

        return TopicoDto.convert(topicos);
    }

    //Since v2.30 Spring Boot, Need to import maven dependency starter-validation
    @PostMapping
    public ResponseEntity<TopicoDto> add(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.convert(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));

    }
}
