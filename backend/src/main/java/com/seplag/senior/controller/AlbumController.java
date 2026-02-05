package com.seplag.senior.controller;

import com.seplag.senior.model.Album;
import com.seplag.senior.repository.AlbumRepository;
import com.seplag.senior.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/albuns")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite a comunicacao com o Frontend
public class AlbumController {

    private final AlbumRepository repository;
    private final FileStorageService fileService;

    // Listagem de albuns filtrada por artista
    @GetMapping("/artista/{artistaId}")
    public ResponseEntity<Page<Album>> listarPorArtista(
            @PathVariable Long artistaId,
            @PageableDefault(size = 10) Pageable pageable) {
        
        return ResponseEntity.ok(repository.findByArtistaId(artistaId, pageable));
    }

    // Endpoint para upload da capa e criacao do album
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCapa(@RequestParam("file") MultipartFile file) {
        // Envia o ficheiro para o MinIO e retorna o nome gerado para ser guardado no banco
        String nomeFicheiro = fileService.uploadFile(file);
        return ResponseEntity.ok(nomeFicheiro);
    }

    // Salvar os dados do album no banco de dados
    @PostMapping
    public ResponseEntity<Album> salvar(@RequestBody Album album) {
        return ResponseEntity.ok(repository.save(album));
    }
}