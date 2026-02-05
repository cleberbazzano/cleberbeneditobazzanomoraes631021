package com.seplag.senior.controller;

import com.seplag.senior.model.Artista;
import com.seplag.senior.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/artistas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite a comunicacao com o Frontend
public class ArtistaController {

    private final ArtistaRepository repository;

    // Listagem com paginacao e busca por nome (Requisito de Performance)
    @GetMapping
    public ResponseEntity<Page<Artista>> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        
        if (nome != null && !nome.isBlank()) {
            return ResponseEntity.ok(repository.findByNomeContainingIgnoreCase(nome, pageable));
        }
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    // Criacao de novo artista
    @PostMapping
    public ResponseEntity<Artista> criar(@RequestBody Artista artista) {
        return ResponseEntity.ok(repository.save(artista));
    }

    // Busca detalhada por ID
    @GetMapping("/{id}")
    public ResponseEntity<Artista> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}