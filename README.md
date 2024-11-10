# API para consulta de gastos parlamentares

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue) ![Docker](https://img.shields.io/badge/Docker--blue) [![LinkedIn](https://img.shields.io/badge/Connect%20on-LinkedIn-blue)](https://www.linkedin.com/in/gabrieudev) ![GPL License](https://img.shields.io/badge/License-GPL-blue)

Seja bem vindo(a) ao meu projeto de **API para consulta de gastos parlamentares**.

## Tabela de conteúdos

- [Introdução](#introdução)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Iniciando](#iniciando)
- [Contribuições](#contribuições)
- [Contato](#contato)

## Introdução

O projeto é baseado em um [desafio](https://github.com/agendaedu/desafio-backend?tab=readme-ov-file) back-end, no qual propõe a construção de uma API, utilizando o framework Rails, que realize o processamento e armazenamento das informações contidas em um arquivo .csv, obtido no [Portal da Câmara dos Deputados](https://dadosabertos.camara.leg.br/swagger/api.html?tab=staticfile#staticfile), e disponibilize algumas rotas para visualização de informações. Sendo assim, este projeto é uma adaptação que utiliza o framework Spring Boot e entrega algumas funcionalidades extras.

## Funcionalidades

- Upload de arquivo .csv.
- Listagem/pesquisa paginada de deputados e despesas.
- Soma, média e maior das despesas de cada deputado.
- Listagem paginada das despesas de um deputado.
- Listagem paginada de despesas por período.
- Integração com o banco de dados PostgreSQL.
- Documentação completa e detalhada utilizando Swagger.
- Conteinerização da API utilizando Docker.

## Tecnologias

- ![Java](https://img.shields.io/badge/Java-17-orange): Linguagem de programação.
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green): Framework usado na construção de APIs REST.
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue): Banco de dados relacional.
- ![Docker](https://img.shields.io/badge/Docker--blue): Tecnologia de conteinerização open-source.

## Iniciando

Siga os seguintes passos para executar o projeto na sua máquina (necessário ter o Docker instalado).

1. Clone o repositório:

   ```bash
   git clone https://github.com/gabrieudev/gastos-parlamentares.git
   ```

2. Vá para a pasta raiz do projeto e execute o comando:

   ```bash
   docker-compose up --build
   ```

3. Acesse a página de [documentação](http://localhost:8080/swagger-ui/index.html) para visualizar todos os endpoints e realizar as requisições.

## Contribuições

Contribuições são muito bem vindas! Caso queira contribuir, faça um fork do repositório e crie um pull request.

## Contato

Caso tenha alguma sugestão ou dúvida, entre em contato comigo em [LinkedIn](https://www.linkedin.com/in/gabrieudev)

---

**Licença:** Esse projeto é licenciado sob os termos da [GNU General Public License (GPL)](LICENSE).
