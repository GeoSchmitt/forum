package com.geoschmitt.forum.repository;

import com.geoschmitt.forum.model.Curso;
import com.geoschmitt.forum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findByNome(String name);

}
