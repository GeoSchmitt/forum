package com.geoschmitt.forum.repository;

import com.geoschmitt.forum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByCursoNome(String name);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :name")
    List<Topico> loadByName(@Param("name") String name);
}
