# Sistema de Eleição com Java e Quarkus

## 🎯 Objetivo

O objetivo deste projeto é criar um sistema distribuído e escalável para eleições, 
contendo aplicações responsáveis para votação, gerenciamento de eleição, 
e consulta de resultados utilizando Java, Docker, MariaDB, Quarkus e Arquitetura Limpa.

Projeto de laboratório da [dio.me] com o especialista [Thiago Poiani]

## 📜 Tecnologias

- Linguagem
  - [Java] 17
- Framework
  - [Quarkus]
- Testabilidade
  - [JUnit] 
  - [Testcontainers]
- ORM
  - [Hibernate]
- Migração
  - [Flyway]
- Conteinerização
  - [Docker]
- Tráfego
  - [Traefik]
- Armazenamento
  - [MariaDB]
  - [Redis]
- Observabilidade
  - [Graylog]
  - [OpenSearch]
  - [MongoDB]
  - [Jaeger Tracing]

## 👨‍💻 Iniciar as aplicações deste projeto

### Docker Compose
Antes de inicar os módulos, é importante subir os containers e executar XXXX, seguem abaixo os comandos a serem executados:

```shell
docker compose up -d reverse-proxy
docker compose up -d jaeger
docker compose up -d mongodb opensearch
docker compose up -d graylog
curl -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW46YWRtaW4=" -H "X-Requested-By: curl" -X POST -v -d '{"title":"udp input","configuration":{"recv_buffer_size":262144,"bind_address":"0.0.0.0","port":12201,"decompress_size_limit":8388608},"type":"org.graylog2.inputs.gelf.udp.GELFUDPInput","global":true}' http://logging.private.dio.localhost/api/system/inputs
docker compose up -d caching database
```

### CI/CD Build
Para as aplicações desenvolvidas, primeiro criar o build para cada aplicação, abaixo existe os comandos para build das três aplicações:

```shell
./cicd-build.sh election-management
./cicd-build.sh voting-app
./cicd-build.sh result-app
```

### CI/CD Blue Green Deployment
Para as aplicações desenvolvidas, primeiro criar o build para cada aplicação, abaixo existe os comandos para build das três aplicações:

```shell
./cicd-blue-green-deployment.sh election-management
./cicd-blue-green-deployment.sh voting-app
./cicd-blue-green-deployment.sh result-app
```


[dio.me]: https://www.dio.me/
[Thiago Poiani]: https://github.com/thpoiani/

[Java]: https://www.java.com/pt-BR/
[Quarkus]: https://quarkus.io/
[Docker]: https://www.docker.com/
[Traefik]: https://doc.traefik.io/traefik/
[MariaDB]: https://mariadb.org/
[Redis]: https://redis.io/
[MongoDB]: https://www.mongodb.com/pt-br
[Graylog]: https://graylog.org/
[OpenSearch]: https://opensearch.org/
[Jaeger Tracing]: https://www.jaegertracing.io/
[JUnit]: https://junit.org/junit5/
[Testcontainers]: https://testcontainers.com/
[Hibernate]: https://hibernate.org/
[Flyway]: https://flywaydb.org/
