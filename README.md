# Laboratorios del Summer Camp 2025

## Java (Spring)

### Instalación

- [JDK](https://www.oracle.com/java/technologies/downloads/)
- [Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2024-03/R/eclipse-jee-2024-03-R-win32-x86_64.zip)
- [Spring Tools 4 for Eclipse](https://spring.io/tools/)
- [Project Lombok](https://projectlombok.org/download)

### Documentación

- https://docs.spring.io/spring-boot/docs/current/reference/html/
- https://docs.spring.io/spring-data/commons/docs/current/reference/html/
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
- https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
- https://docs.spring.io/spring-data/redis/docs/current/reference/html/
- https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#spring-web
- https://docs.spring.io/spring-data/rest/docs/current/reference/html/
- https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#spring-cloud-loadbalancer
- https://docs.spring.io/spring-cloud-config/docs/current/reference/html/
- https://docs.spring.io/spring-security/reference/index.html

### Ejemplos

- https://github.com/spring-projects/spring-data-examples
- https://github.com/spring-projects/spring-data-rest-webmvc
- https://github.com/spring-projects/spring-hateoas-examples
- https://github.com/spring-projects/spring-amqp-samples
- https://github.com/rabbitmq/rabbitmq-tutorials/tree/main/spring-amqp
- https://github.com/spring-projects/spring-kafka/tree/main/samples
- https://github.com/elastic/examples

### Paquetes Java

- https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-java-5.1.49.zip  
- https://sourceforge.net/projects/hibernate/files/hibernate-orm/5.6.5.Final/hibernate-release-5.6.5.Final.zip/download
- https://eclipse.dev/eclipselink/#download

## Base de datos de ejemplos

### Clientes de bases de datos (opcionales)

- [HeidiSQL](https://www.heidisql.com/download.php)
- [DBeaver Community](https://dbeaver.io/download/)
- [MongoDB Compass](https://www.mongodb.com/try/download/compass)

### Documentación

- [Página principal Sakila](https://dev.mysql.com/doc/sakila/en/)
- [Diagrama de la BD Sakila](http://trifulcas.com/wp-content/uploads/2018/03/sakila-er.png)

## Servidores en Docker

### Instalación

- [WSL 2 feature on Windows](https://learn.microsoft.com/es-es/windows/wsl/install)
- Docker
  - [Docker Desktop](https://www.docker.com/get-started/)
- Alternativas a Docker Desktop
  - [Podman](https://podman.io/docs/installation)
  - [Rancher Desktop](https://rancherdesktop.io/)

### Bases de datos

#### MySQL

    docker run -d --name mysql-sakila -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 jamarton/mysql-sakila

#### MongoDB

    docker run -d --name mongodb -p 27017:27017 -v .:/externo jamarton/mongodb-contactos

#### Redis

    docker run -d --name redis -p 6379:6379 redis

#### Apache Cassandra

    docker run -d --name cassandra -p 9042:9042 -v .:/externo jamarton/cassandra-videodb
      
    docker exec -it cassandra sh -c /init-db.sh

#### ELK (supervisión)

Comando:

      cd docker-compose\elk && docker compose up -d
