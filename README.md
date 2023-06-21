# Spring Boot E-commerce bookstore web application

This project is a straightforward example of an e-commerce bookstore web application. It follows a monolithic
architecture (Java MVC) approach with server-side rendering using Java template engine.

## Features

The application offers the following features:

- **User Registration and Authentication:** Users can create accounts and securely log in.
- **Browse Book Listings:** Users can view a comprehensive list of available books.
- **Sorting by Category:** Books can be sorted by different categories for easier navigation.
- **Title Search:** Users can search for specific books by their titles.
- **Individual Book Pages:** Each book has its own dedicated page with detailed information.
- **Book Reviews:** Users can leave reviews and ratings for books they have read.
- **Add to Cart:** Books can be added to the shopping cart for easy purchasing.

## Tech Stack

- Java
- Spring Boot 3 MVC
- Thymeleaf (Template Engine)
- Javascript
- JPA/Hibernate
- Postgres
- Docker
- JWT Authentication
- JUnit 5 (Unit and Integration testing)

### Frontend (Thymeleaf Template Engine, Javascript)

This project utilizes the Thymeleaf template engine for the frontend, offering a powerful server-side rendering
approach. By combining Thymeleaf with JavaScript, the web pages become dynamic, and DOM hydration enhances
interactivity.

#### Benefits of Thymeleaf Template Engine:

- **Server-side Rendering**: Thymeleaf generates HTML on the server, resulting in faster initial page loads and improved
  SEO.
- **Integration with Java**: Thymeleaf seamlessly integrates with Java frameworks like Spring Boot, facilitating data
  integration.
- **Rich Templating Features**: Thymeleaf provides an expressive language, conditional rendering, iteration, and form
  handling capabilities.
- **Flexibility and Extensibility**: Thymeleaf's modular architecture allows for easy extension and integration with
  other libraries.

JavaScript and DOM hydration are used to enhance the web page's dynamic behavior. This approach combines the benefits of
server-side rendering with client-side interactivity, creating a seamless user experience.

### Backend (Java Spring Boot 3)

The backend of this project is developed using Java Spring Boot 3, leveraging the benefits of the JVM and
object-oriented programming.

#### Benefits of Java and Spring Boot for Web Applications:

- **Platform Independence**: Java programs run on the JVM, providing platform independence and portability.
- **Strongly Typed Language**: Java's static typing allows for early error detection, ensuring reliable code.
- **Object-Oriented Programming**: Java's OOP principles promote modular and reusable code.
- **Rich Standard Library**: Java offers a comprehensive library for common tasks, simplifying development.
- **Memory Management**: Java's built-in garbage collection automates memory management.
- **Concurrency Support**: Java provides robust concurrency features for multi-threaded applications.
- **Large Community and Ecosystem**: Java has a thriving community and extensive ecosystem of libraries and tools.

By utilizing Java Spring Boot, this project benefits from platform independence, reliable typing, modular code
organization, a rich standard library, memory management automation, concurrency support, and community-driven support.

### Authentication (JWT) with Spring Security

This project implements JWT authentication using Spring Security, providing a secure and efficient authentication
mechanism for your application.

#### Benefits of JWT Authentication with Spring Security:

- **Stateless and Scalable**: JWT authentication is stateless, enabling easy scalability without the need for shared
  session storage.
- **Enhanced Security**: JWTs are digitally signed, ensuring integrity and preventing tampering. They can also be
  encrypted for added security.
- **Decentralized and Interoperable**: JWTs can be seamlessly shared across different systems, making them ideal for
  microservices architectures and component integration.
- **Reduced Database Lookups**: JWTs contain user information and permissions, eliminating the need for frequent
  database queries and improving performance.
- **Improved User Experience**: Custom claims in JWTs allow for personalized user experiences, reducing the need for
  additional database queries.

Spring Security simplifies JWT authentication implementation, offering the following benefits:

- **Easy Configuration**: Spring Security provides a straightforward setup for JWT authentication using annotations or
  configuration files.
- **Token Validation and Security**: Spring Security handles JWT token parsing, signature verification, and expiration
  checks.
- **Fine-Grained Authorization**: Granular access control rules can be defined based on roles, permissions, or custom
  conditions.
- **User Management Integration**: Spring Security seamlessly integrates with user management features, simplifying JWT
  authentication integration.
- **Security Filters**: Pre-configured filters in Spring Security handle token validation, authentication, and
  authorization tasks.

By leveraging Spring Security for JWT authentication, this project gains simple configuration, robust token validation,
fine-grained authorization, user management integration, and security filters.

### Database (PostgreSQL) with JPA/Hibernate and Docker

This project utilizes PostgreSQL as the database, JPA/Hibernate for object-relational mapping, and Docker containers for
easy setup and deployment.

#### Benefits:

- **Reliability and Stability**: PostgreSQL is a robust and reliable RDBMS, ensuring data integrity and handling
  high-traffic production environments.
- **ACID Compliance**: PostgreSQL follows ACID principles, providing transactional integrity and data consistency.
- **Scalability**: PostgreSQL supports horizontal scalability, allowing the ecommerce website to handle growing data and
  user demands.
- **Flexibility**: PostgreSQL offers advanced features and supports complex queries, user-defined types, and custom
  functions.
- **Data Integrity and Constraints**: PostgreSQL ensures data integrity with various constraints.
- **Extensibility**: PostgreSQL allows the creation of custom extensions, enhancing functionality.

JPA/Hibernate benefits:

- **Simplified Database Interaction**: JPA abstracts the database details, simplifying database operations using Java
  objects.
- **Portability**: JPA provides a vendor-neutral API, enabling easier migration between different database systems.
- **Caching and Performance Optimization**: Hibernate includes caching mechanisms, optimizing performance and reducing
  database round trips.
- **Database Agnostic**: JPA/Hibernate supports multiple database systems, including PostgreSQL.

Benefits of using Docker containers for PostgreSQL:

- **Easy Setup and Deployment**: Docker provides a lightweight and portable solution for setting up and deploying
  PostgreSQL consistently.
- **Isolation and Security**: Docker containers isolate the database, ensuring security and preventing interference with
  other components.
- **Portability**: Docker containers can be easily moved and deployed across different systems.

By utilizing PostgreSQL with JPA/Hibernate and Docker containers, this project benefits from a reliable RDBMS,
simplified database interactions, improved performance, and easy deployment.

### Testing (JUnit 5) with Mockito and MockMvc

This project utilizes JUnit 5 as the testing framework, along with Mockito for unit testing and MockMvc for integration
testing.

#### Benefits of JUnit 5:

- **Robust and Mature**: JUnit 5 is a widely adopted and well-established testing framework with extensive community
  support.
- **Modular and Extensible**: JUnit 5 offers a modular architecture and extension mechanisms for customizing test
  behavior.
- **Improved Test Organization**: JUnit 5 introduces features for better test organization and readability.
- **Flexible Assertions**: JUnit 5 provides expressive and flexible assertion methods for verifying expected results.
- **Parallel Test Execution**: JUnit 5 supports parallel test execution, reducing test execution time.

Benefits of using Mockito for unit testing:

- **Isolation**: Mockito helps isolate code under test by mocking external dependencies.
- **Behavior Verification**: Mockito enables verification of expected interactions between objects.
- **Flexible Mocking**: Mockito allows for customizing behaviors, stubbing method responses, and verifying method
  invocations.

Benefits of using MockMvc for integration testing:

- **Full Controller Testing**: MockMvc facilitates testing the entire request-response cycle for web applications.
- **Flexible and Expressive API**: MockMvc provides a rich API for constructing requests, setting headers, validating
  responses, and handling different scenarios.
- **Integration with Spring Boot**: MockMvc seamlessly integrates with Spring Boot, simplifying setup and execution of
  integration tests.

By leveraging JUnit 5, Mockito, and MockMvc, this project benefits from a robust testing framework, isolated unit tests,
comprehensive integration testing, and improved test organization.

Please refer to the relevant documentation for more details on implementing testing with JUnit 5, Mockito, and MockMvc.

### Setting Up Postgres DB with Docker

#### Create postgres DB

`docker compose up -d`

#### Access postgres DB

`docker exec -it {name} bash
psql -U {username}`

#### Connect to DB

`\c {database}`

#### Display Tables

```
\dt
SELECT * FROM book;
```

