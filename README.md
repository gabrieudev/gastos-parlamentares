<h1 align="center" style="font-weight: bold;">Gastos parlamentares 🏛️</h1>

<p align="center">
    <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java"/>
    <img src="https://img.shields.io/badge/Javascript-000?style=for-the-badge&logo=javascript" alt="javascript"/>
    <img src="https://img.shields.io/badge/typescript-D4FAFF?style=for-the-badge&logo=typescript" alt="typescript"/>
    <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="spring"/>
    <img src="https://img.shields.io/badge/React-005CFE?style=for-the-badge&logo=react" alt="react"/>
    <img src="https://img.shields.io/badge/Next-black?style=for-the-badge&logo=next.js&logoColor=white" alt="next"/>
    <img src="https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white" alt="tailwind"/>
    <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="postgres"/>
    <img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white" alt="redis"/>
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="docker"/>
    <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" alt="swagger"/>
</p>

<p align="center">
 <a href="#sobre">Sobre</a> • 
 <a href="#inicio">Início</a> • 
  <a href="#rotas">Rotas da aplicação</a> • 
 <a href="#contribuir">Contribuir</a>
</p>

<p align="center">
    <img src="./docs/images/grafico.png" alt="Imagem Exemplo" width="400px">
</p>

<h2 id="sobre">📌 Sobre</h2>

O projeto é baseado em um [desafio](https://github.com/agendaedu/desafio-backend?tab=readme-ov-file), que propõe a construção de uma API utilizando o framework Ruby on Rails, capaz de processar e armazenar informações contidas em um arquivo CSV. Este arquivo pode ser obtido no [Portal da Câmara dos Deputados](https://dadosabertos.camara.leg.br/swagger/api.html?tab=staticfile#staticfile). A API deve também disponibilizar algumas rotas para a visualização das informações. Este projeto é uma adaptação que utiliza os frameworks Spring Boot e Next.js para construir o backend e a interface com dashboard.

<h2 id="inicio">🚀 Início</h2>

<h3>Pré-requisitos</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)

<h3>Clonando o projeto</h3>

```bash
git clone https://github.com/gabrieudev/gastos-parlamentares.git
```

<h3>Iniciando</h3>

```bash
cd gastos
docker compose up -d
```

<h2 id="rotas">📍 Rotas da aplicação</h2>

| Rota                  | Descrição                                                                                                                                                                  |
| --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| <kbd>/</kbd>          | Página inicial que contém o formulário para upload do arquivo CSV                                                                                                          |
| <kbd>/dashboard</kbd> | Página principal da aplicação. Contém funcionalidades como listagem paginada dos deputados, seleção de deputado e exibição de informações através de um gráfico interativo |

<h2 id="contribuir">📫 Contribuir</h2>

Contribuições são muito bem-vindas! Caso queira contribuir, faça um fork do repositório e crie um pull request.

1. `git clone https://github.com/gabrieudev/gastos-parlamentares.git`
2. `git checkout -b feature/NOME`
3. Siga os padrões de commit
4. Abra um Pull Request explicando o problema resolvido ou funcionalidade criada. Se possível, adicione screenshots de modificações visuais e aguarde a revisão!

<h3>Documentações que podem ajudar</h3>

[📝 Como criar um Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Padrão de commits](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
