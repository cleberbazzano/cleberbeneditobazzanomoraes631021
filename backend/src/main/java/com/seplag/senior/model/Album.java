package com.seplag.senior.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "album")
@Data
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "capa_url")
    private String capaUrl; // Caminho/ID da imagem no MinIO

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @Column(name = "data_lancamento")
    private LocalDateTime dataLancamento = LocalDateTime.now();
}