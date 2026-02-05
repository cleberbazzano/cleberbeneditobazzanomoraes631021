package com.seplag.senior.repository;

import com.seplag.senior.model.Artista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    // Permite procurar artistas pelo nome ignorando maiusculas/minusculas
    Page<Artista> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}