Projeto Full Stack Senior – Gestao de Artistas e Albuns

Candidato: Cleber Benedito Bazzano Moraes
Vaga: Engenheiro de Computacao Senior
Numero da inscricao: 16492
Processo Seletivo: Edital n. 001/2026/SEPLAG

1. Apresentacao do Projeto
Este projeto consiste no desenvolvimento de uma solucao Full Stack para gerenciamento de discografia musical, contemplando o cadastro de artistas e seus respectivos albuns, upload e gerenciamento de capas de discos, alem de integracao com sistemas externos regionais.

A solucao foi concebida com foco em arquitetura, seguranca, escalabilidade e boas praticas de engenharia de software, sendo integralmente containerizada para garantir portabilidade, reprodutibilidade do ambiente e facilidade de execucao em diferentes contextos.

2. Tecnologias Utilizadas
Backend
Linguagem: Java 17
Framework: Spring Boot 3
Banco de Dados: PostgreSQL 15
Versionamento de Schema: Flyway
Armazenamento de Arquivos: MinIO (compativel com AWS S3)
Seguranca: Spring Security + JWT (JSON Web Token)
Documentacao da API: OpenAPI 3.0 (Swagger)

Frontend
Framework: React.js
Estilizacao: Tailwind CSS
Gerenciamento de Estado: Context API e Hooks (inspirado no padrao BehaviorSubject)
Build Tool: Vite
Infraestrutura
Containerizacao: Docker e Docker Compose

3. Arquitetura da Solucao
A aplicacao foi projetada seguindo o padrao de Arquitetura em Camadas (Layered Architecture) e os princípios REST, priorizando baixo acoplamento, alta coesao e manutenibilidade.

Camada de Apresentcao (Frontend):
SPA (Single Page Application) desenvolvida em React, responsavel pela interacao com o usuario e consumo da API REST. Implementa abstracao das chamadas HTTP, reduzindo o acoplamento com a camada backend.

Camada de Controle (Controllers):
Exposicao dos endpoints REST versionados (/v1), validacao de dados de entrada e orquestracao das requisicaes.

Camada de Servico (Services):
Concentra toda a logica de negocio da aplicacao, incluindo regras de dominio, sincronizacao de dados regionais e tratamento de arquivos.

Camada de Persistencia (Repositories):
Responsavel pelo acesso ao banco de dados PostgreSQL utilizando Spring Data JPA.

Camada de Integracao (External):
Modulo dedicado a comunicacao com APIs externas (Regionais) e com o servico de armazenamento de arquivos (MinIO).

4. Estrutura de Dados (Banco de Dados)
O esquema do banco de dados e versionado e controlado via Flyway (/db/migration), garantindo rastreabilidade e consistência entre ambientes.
Usuario: Controle de acesso e autenticacao (login/senha).
Artista: Entidade principal do dominio, contendo dados cadastrais do artista.
Album: Entidade associada ao artista (relacao N:1), contendo metadados e referencia da capa (URL).
Regional: Tabela de cache local utilizada para integracao externa, com controle de status por meio de flag ativo.

5. Decisoes Tecnicas Relevantes
5.1 Armazenamento de Arquivos com MinIO
Motivacao: Simular um ambiente real de cloud storage (AWS S3) em ambiente local.

Implementacao:
As imagens nao sao armazenadas no banco de dados como BLOB. Apenas a URL e persistida, enquanto os arquivos ficam no MinIO.
Para maior seguranca, sao utilizadas Presigned URLs com expiracao de 30 minutos, evitando exposicao publica direta do bucket.

5.2 Sincronizacao de Regionais (Requisito de Nivel Senior)
Estrategia: Implementacao de um Scheduled Task para sincronizacao periodica com a API externa.

Criterio Algoritmico:
Para atender ao requisito de menor complexidade algorítmica, foi utilizada uma estrutura de dados do tipo Map (HashMap) para comparacao em memoria, atingindo complexidade proxima a O(n) e evitando consultas aninhadas ao banco (O(n2)).
Registro novo na API externa - Insert
Registro ausente na API externa - Update (ativo = false)
Registro alterado - Update (ativo = false) + Insert (novo registro)

5.3 Autenticacao e Seguranca
JWT (JSON Web Token) foi adotado para manter a API stateless, eliminando sessões no servidor e facilitando eventual escalabilidade horizontal.

6. Execucao do Projeto
A aplicacao encontra-se totalmente dockerizada, não sendo necessaria a instalacao local de Java ou Node.js.

Pre-requisitos:
Docker Desktop instalado e em execucao
Git
Passo a Passo
Clone o repositorio:
git clone https://github.com/cleberbazzano/fullstack.git
cd fullstack

Suba os containers:
docker-compose up --build

Aguarde a inicializacao completa (download de dependencias do Maven e Node).

Acessos
Frontend: http://localhost:5173
Login: admin
Senha: admin123 (ou conforme definido na migration)
Backend (Swagger): http://localhost:8080/swagger-ui.html
Utilize /auth/login para obter o token e autorizar no Swagger.
MinIO Console: http://localhost:9001
Usuário: minioadmin
Senha: minioadmin

7. Status de Implementacao
Backend
 CRUD de Artistas e Albuns
 Paginacao e Ordenacao
 Upload de Imagens (MinIO)
 Seguranca (JWT e CORS)
 Documentacao Swagger
 Migrations com Flyway
 Integracao com Regionais (Sync Service)
 Health Checks (Actuator)
 Testes Unitarios Automatizados (pendente)
 WebSocket (pendente)

Frontend
 Login e Autenticacao
 Listagem de Artistas (Cards)
 Detalhamento de Artistas e Albuns
 Cadastro e Edicao
 Upload de Capas

8. Justificativa de Priorizacao

Diante da complexidade do escopo e das restricaes de prazo, optei por priorizar a entrega de uma arquitetura backend solida, segura e aderente as boas praticas, assegurando o pleno funcionamento do nucleo da aplicacao (Core Business). Foram priorizados aspectos estruturais como containerizacao, seguranca, integracao com armazenamento S3 (MinIO) e sincronizacao com sistemas externos regionais.

Funcionalidades complementares, como comunicacao em tempo real via WebSocket e cobertura total de testes unitarios automatizados, foram conscientemente postergadas. Essa decisao teve como objetivo garantir estabilidade, consistencia arquitetural e qualidade do codigo entregue, mantendo tais itens claramente mapeados como evolucao futura do sistema.