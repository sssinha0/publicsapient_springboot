```markdown
# 🍴 Public Sapient Recipe Backend

A Spring Boot–based backend service for orchestrating, storing, and searching recipes.  
This project integrates with an external recipe API (DummyJSON or other providers) and provides a resilient, searchable backend to serve the Angular frontend.

---

## 🚀 Features

- **Recipe Orchestration**  
  - Fetches recipes from external API in batches  
  - Resilient API calls with **Resilience4j** (`@CircuitBreaker`, `@Retry`)  
  - Automatic fallback handling on failures  

- **Persistence Layer**  
  - In-memory **H2 Database** for fast development  
  - JPA/Hibernate for entity management  
  - Automatic schema generation  

- **Search & Filtering**  
  - **Hibernate Search** with Lucene backend  
  - Full-text search across `name`, `cuisine`, and `ingredients`  
  - Custom analyzers for English text  
  - Filter recipes by `cuisine`, `mealType`, `rating`, `prepTime`, etc.  

- **REST API**  
  - Endpoints to load, search, and fetch recipes  
  - Supports pagination & sorting  
  - JSON DTOs for frontend compatibility  

- **Error Handling**  
  - Global exception handling  
  - Custom exceptions (`ExternalServiceException`)  

- **Modern Java Practices**  
  - `lombok` for boilerplate-free entities and DTOs  
  - Streams & collectors for mapping data  
  - Service-oriented architecture  

---

## 🛠️ Tech Stack

- **Java 17**  
- **Spring Boot 3**  
- **Spring Data JPA**  
- **Hibernate Search (Lucene)**  
- **Resilience4j** (circuit breaker, retry)  
- **WebClient** (for non-blocking API calls)  
- **H2 Database** (in-memory, development mode)  
- **Maven** (build tool)  
- **Lombok** (annotations for boilerplate reduction)  

---

## 📂 Project Structure

```

src/main/java/com/publicsapient/demo
├── controller       # REST controllers (search, recipes)
├── dto              # Data Transfer Objects
├── exceptions       # Custom exception classes
├── model            # JPA entities
├── repository       # Spring Data repositories
├── service          # Business logic (orchestration, search)
└── utility          # Utility classes (JSON, helpers)

````

---

## ⚙️ Configuration

**application.yml**
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:recipesdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driverClassName: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true
      path: /h2-console

hibernate:
  search:
    backend:
      type: lucene
````

* Access H2 console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

  * JDBC URL: `jdbc:h2:mem:recipesdb`
  * User: `sa`
  * Password: *(blank)*

---

## ▶️ Running the Application

### Prerequisites

* Java 17+
* Maven 3.8+

## 📡 API Endpoints

### Load Recipes

```http
POST /orchestrate/load
```

Fetches recipes from external API and stores them in H2.

### Search Recipes

```http
GET /recipes/search?q=masala&cuisine=Indian&mealType=Breakfast&page=0&size=10
```

Searches recipes by text query, cuisine, meal type, etc.

### Get Recipe by ID

```http
GET /recipes/{id}
```

### Random Search

```http
GET /recipes/random?q=chicken
```

---

---

## 📊 Future Improvements

* Switch H2 → PostgreSQL/MySQL for production
* Add caching with Redis
* Add authentication (JWT-based)
* Dockerize backend
* CI/CD pipeline with GitHub Actions

---

## 👩‍💻 Contributing

1. Fork the repo
2. Create a feature branch
3. Commit your changes with clear messages
4. Open a pull request

---

## 📸 Screenshots 
<img width="987" height="588" alt="Screenshot from 2025-09-08 10-58-39" src="https://github.com/user-attachments/assets/5e42eb09-e4d3-4034-be2b-a79bb2379036" />
---

