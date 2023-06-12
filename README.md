# Spring Boot E-commerce bookstore example

## Tech Stack

- Java
- Spring Boot 3
- JPA/Hibernate
- Postgres
- Thymeleaf
- Javascript
- JWT Authentication

### Setting Up Postgres DB

#### Create postgres DB

"docker compose up -d"

#### Access postgres DB

docker exec -it {name} bash
psql -U {username}

#### Connect to DB

\c {database}

#### Display Tables

\dt
SELECT * FROM book;

