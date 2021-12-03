package com.geoschmitt.forum.controller;

import com.geoschmitt.forum.controller.dto.TopicoDetailDto;
import com.geoschmitt.forum.controller.dto.TopicoDto;
import com.geoschmitt.forum.controller.dto.TopicoForm;
import com.geoschmitt.forum.controller.dto.TopicoUpdateDto;
import com.geoschmitt.forum.model.Curso;
import com.geoschmitt.forum.model.Topico;

import com.geoschmitt.forum.repository.CursoRepository;
import com.geoschmitt.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
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
    @Transactional
    public ResponseEntity<TopicoDto> add(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.convert(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    //Since v2.5 Spring Boot, getOne is deprecated
    @GetMapping("/{id}")
    public TopicoDetailDto detail(@PathVariable Long id){
        Topico topico = topicoRepository.getById(id);
        return new TopicoDetailDto(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> update(@PathVariable Long id, @RequestBody @Valid TopicoUpdateDto form){
        Topico topico = form.update(id, topicoRepository);
        return ResponseEntity.ok(new TopicoDto(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable Long id){
        topicoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
