package com.seplag.senior.repository;

import com.seplag.senior.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    // Permite listar albuns filtrando pelo ID do artista
    Page<Album> findByArtistaId(Long artistaId, Pageable pageable);
}