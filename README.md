Projeto Full Stack Sênior – Gestão de Artistas e Álbuns

Candidato: Cleber Benedito Bazzano Moraes
Vaga: Engenheiro de Computação – Sênior
Processo Seletivo: Edital nº 001/2026/SEPLAG

1. Apresentação do Projeto
Este projeto consiste no desenvolvimento de uma solução Full Stack para gerenciamento de discografia musical, contemplando o cadastro de artistas e seus respectivos álbuns, upload e gerenciamento de capas de discos, além de integração com sistemas externos regionais.

A solução foi concebida com foco em arquitetura, segurança, escalabilidade e boas práticas de engenharia de software, sendo integralmente containerizada para garantir portabilidade, reprodutibilidade do ambiente e facilidade de execução em diferentes contextos.

2. Tecnologias Utilizadas
Backend
Linguagem: Java 17
Framework: Spring Boot 3
Banco de Dados: PostgreSQL 15
Versionamento de Schema: Flyway
Armazenamento de Arquivos: MinIO (compatível com AWS S3)
Segurança: Spring Security + JWT (JSON Web Token)
Documentação da API: OpenAPI 3.0 (Swagger)

Frontend
Framework: React.js
Estilização: Tailwind CSS
Gerenciamento de Estado: Context API e Hooks (inspirado no padrão BehaviorSubject)
Build Tool: Vite
Infraestrutura
Containerização: Docker e Docker Compose

3. Arquitetura da Solução
A aplicação foi projetada seguindo o padrão de Arquitetura em Camadas (Layered Architecture) e os princípios REST, priorizando baixo acoplamento, alta coesão e manutenibilidade.

Camada de Apresentação (Frontend):
SPA (Single Page Application) desenvolvida em React, responsável pela interação com o usuário e consumo da API REST. Implementa abstração das chamadas HTTP, reduzindo o acoplamento com a camada backend.

Camada de Controle (Controllers):
Exposição dos endpoints REST versionados (/v1), validação de dados de entrada e orquestração das requisições.

Camada de Serviço (Services):
Concentra toda a lógica de negócio da aplicação, incluindo regras de domínio, sincronização de dados regionais e tratamento de arquivos.

Camada de Persistência (Repositories):
Responsável pelo acesso ao banco de dados PostgreSQL utilizando Spring Data JPA.

Camada de Integração (External):
Módulo dedicado à comunicação com APIs externas (Regionais) e com o serviço de armazenamento de arquivos (MinIO).

4. Estrutura de Dados (Banco de Dados)
O esquema do banco de dados é versionado e controlado via Flyway (/db/migration), garantindo rastreabilidade e consistência entre ambientes.
Usuário: Controle de acesso e autenticação (login/senha).
Artista: Entidade principal do domínio, contendo dados cadastrais do artista.
Álbum: Entidade associada ao artista (relação N:1), contendo metadados e referência da capa (URL).
Regional: Tabela de cache local utilizada para integração externa, com controle de status por meio de flag ativo.

5. Decisões Técnicas Relevantes
5.1 Armazenamento de Arquivos com MinIO
Motivação: Simular um ambiente real de cloud storage (AWS S3) em ambiente local.

Implementação:
As imagens não são armazenadas no banco de dados como BLOB. Apenas a URL é persistida, enquanto os arquivos ficam no MinIO.
Para maior segurança, são utilizadas Presigned URLs com expiração de 30 minutos, evitando exposição pública direta do bucket.

5.2 Sincronização de Regionais (Requisito de Nível Sênior)
Estratégia: Implementação de um Scheduled Task para sincronização periódica com a API externa.

Critério Algorítmico:
Para atender ao requisito de menor complexidade algorítmica, foi utilizada uma estrutura de dados do tipo Map (HashMap) para comparação em memória, atingindo complexidade próxima a O(n) e evitando consultas aninhadas ao banco (O(n²)).
Registro novo na API externa → Insert
Registro ausente na API externa → Update (ativo = false)
Registro alterado → Update (ativo = false) + Insert (novo registro)

5.3 Autenticação e Segurança
JWT (JSON Web Token) foi adotado para manter a API stateless, eliminando sessões no servidor e facilitando eventual escalabilidade horizontal.

6. Execução do Projeto
A aplicação encontra-se totalmente dockerizada, não sendo necessária a instalação local de Java ou Node.js.

Pré-requisitos:
Docker Desktop instalado e em execução
Git
Passo a Passo
Clone o repositório:
git clone https://github.com/cleberbazzano/fullstack.git
cd fullstack

Suba os containers:
docker-compose up --build

Aguarde a inicialização completa (download de dependências do Maven e Node).

Acessos
Frontend: http://localhost:5173
Login: admin
Senha: admin123 (ou conforme definido na migration)
Backend (Swagger): http://localhost:8080/swagger-ui.html
Utilize /auth/login para obter o token e autorizar no Swagger.
MinIO Console: http://localhost:9001
Usuário: minioadmin
Senha: minioadmin

7. Status de Implementação
Backend
 CRUD de Artistas e Álbuns
 Paginação e Ordenação
 Upload de Imagens (MinIO)
 Segurança (JWT e CORS)
 Documentação Swagger
 Migrations com Flyway
 Integração com Regionais (Sync Service)
 Health Checks (Actuator)
 Testes Unitários Automatizados (pendente)
 WebSocket (pendente)

Frontend
 Login e Autenticação
 Listagem de Artistas (Cards)
 Detalhamento de Artistas e Álbuns
 Cadastro e Edição
 Upload de Capas

8. Justificativa de Priorização

Diante da complexidade do escopo e das restrições de prazo, optei por priorizar a entrega de uma arquitetura backend sólida, segura e aderente às boas práticas, assegurando o pleno funcionamento do núcleo da aplicação (Core Business). Foram priorizados aspectos estruturais como containerização, segurança, integração com armazenamento S3 (MinIO) e sincronização com sistemas externos regionais.

Funcionalidades complementares, como comunicação em tempo real via WebSocket e cobertura total de testes unitários automatizados, foram conscientemente postergadas. Essa decisão teve como objetivo garantir estabilidade, consistência arquitetural e qualidade do código entregue, mantendo tais itens claramente mapeados como evolução futura do sistema.