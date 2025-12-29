# Technical Test – Scaffold Clean Architecture

Backend application developed as a technical test to manage **franchises, branches, and products**, including **stock management** and **queries to retrieve the product with the highest stock per branch**.

The application is built using **Spring Boot WebFlux** and follows a **Scaffold Clean Architecture** approach.

---

## 1. Tech Stack

- Java 21
- Spring Boot (WebFlux)
- Project Reactor (Mono / Flux)
- R2DBC
- PostgreSQL
- Docker
- Gradle

---

## 2. Architecture

- **Scaffold Clean Architecture**

---

## 3. Prerequisites

Before running the application, ensure you have installed:

- Docker
- Java 21
- Gradle (optional, wrapper included)

---

## 4. Database Setup

### 4.1 Create PostgreSQL container

```bash
docker run --name postgres \
  -e POSTGRES_USER=<your_user> \
  -e POSTGRES_PASSWORD=<your_password> \
  -e POSTGRES_DB=<your_database_name> \
  -p 5432:5432 \
  -v postgres_data:/var/lib/postgresql/data \
  -d postgres:16
```

---

### 4.2 Create database tables

```sql
CREATE TABLE franchise (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_date TIMESTAMP
);

CREATE TABLE branch (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    franchise_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    created_date TIMESTAMP,

    CONSTRAINT fk_branch_franchise
        FOREIGN KEY (franchise_id)
        REFERENCES franchise(id)
        ON DELETE CASCADE
);

CREATE TABLE product (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    stock INT NOT NULL,
    created_date TIMESTAMP,

    CONSTRAINT fk_product_branch
        FOREIGN KEY (branch_id)
        REFERENCES branch(id)
        ON DELETE CASCADE
);
```

---

## 5. Environment Variables

### Linux / macOS

```bash
export DB_DATABASE=postgres
export DB_HOST=docker ip database
export DB_PORT=5432
export DB_SCHEMA=public
export DB_PASSWORD=<your_password>
export DB_USERNAME=postgres
```

### Windows (PowerShell)

```powershell
$env:DB_DATABASE="postgres"
$env:DB_HOST="<docker ip database>"
$env:DB_PORT="5432"
$env:DB_SCHEMA="public"
$env:DB_PASSWORD="<your_password>"
$env:DB_USERNAME="postgres"
```
---

## 6. How to Run the Application

### Clone the repository

```bash
git clone https://github.com/juandmantilla/technical_test.git
cd technical_test
```

### Build the application

```bash
./gradlew clean build
```

### Run the application

```bash
./gradlew bootRun
```

The application will start at:

```
http://localhost:8080
```

---

## 7. Test the Application
You will find the collection of requests in the following directory.
```
/collection/Technical-Test.postman_collection.json
```
### Note
Remember to keep the web application open to make requests.
