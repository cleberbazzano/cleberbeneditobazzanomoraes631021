-- Tabela de Regionais (Requisito de Integração Sênior)
CREATE TABLE regional (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

-- Tabela de Artistas (Exemplo: Serj Tankian, Michel Teló)
CREATE TABLE artista (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Álbuns ligados aos Artistas
CREATE TABLE album (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    capa_url VARCHAR(500),
    artista_id INTEGER REFERENCES artista(id) ON DELETE CASCADE,
    data_lancamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Usuários para acesso ao Sistema
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);