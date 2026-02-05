package com.seplag.senior.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "artista")
@Data // Gera automaticamente Getters, Setters, equals e hashCode
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    // Relacao 1 para N: Um artista pode ter varios albuns
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL)
    private List<Album> albuns;
}