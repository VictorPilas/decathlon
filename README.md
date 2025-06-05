# Decathlon API

REST API for managing teammates.

---

## 💡 Features

- Java 21 + Spring Boot
- `/prices`
- Configured with Docker and Docker Compose.
- Interactive API documentation available in Swagger UI

---


## 📚 Manually Build and Run the Project


### 1. **Build the project**

```bash
cd <proyect-dir>
./mvnw clean install
```

### 2. **Run the application**

```bash
./mvnw spring-boot:run
```

By default, the service will be exposed at:
```
http://localhost:8080
```

---


## 🗃️ H2 Database Access

The project uses an in-memory H2 database for development and testing.

### Web Console

You can access the H2 console at:

http://localhost:8080/h2-console

### Default Credentials

- **JDBC URL:** `jdbc:h2:mem:demodb`
- **Username:** `sa`
- **Password:** *(leave empty)*

---

## 📦 Build and Deploy with Docker

### 1. **Build the Docker image**

```bash
docker build -t victor220686/decathlon-service:latest .
```

---

## 🛠️ Start the Service with Docker Compose

### 1. **Configurable variables in `.env`**

```env
DOCKERHUB_USER=victor220686
IMAGE_TAG=latest
APP_PORT=8080
```

### 2. **Run Docker Compose**

```bash
docker-compose up -d --build 
```

The application will be available at:
```
http://localhost:8080/api/teammates
```

---

## 📈 Access Swagger UI

Once the service is up, you can access the interactive API documentation at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 💡 Using Kafka

### ** POST using kafka**

```bash
 docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list 
```
This command open the "Kafka producer" and you can send message to the consumer like this:
```bash
 {"firstName":"Luis","lastName":"Gómez","email":"luis.gomez@test.com","position":"Backend","startDate":"2023-01-01","active":true} 
```
---


## 💡 Using the Endpoint

### **GET `/api/teammates`**

| Parameter | Type | Description  |
|-----------|------|--------------|
| `id`      | UUID | UUID format  |


**Example:**

```
GET http://localhost:8080/api/teammates?id=123e4567-e89b-42d3-a456-556642440000
```

Get a teammate

### **GET `/api/teammates`**



**Example:**

```
GET http://localhost:8080/api/teammates
```

Get all the teammates

## **PUT `/api/teammates`**

| Parameter | Type | Description |
|-----------|------|-------------|
| `id`      | UUID | UUID format |
| Body      | JSON | JSON format |


**Example:**

```
PUT http://localhost:8080/api/teammates?id=123e4567-e89b-42d3-a456-556642440000
Body -> {"firstName":"Luis","lastName":"Gómez","email":"luis.gomez@test.com","position":"Backend","startDate":"2023-01-01","active":true}
```

Modify a teammate data

## **PUT `/api/teammates`**

| Parameter | Type | Description |
|-----------|------|-------------|
| `id`      | UUID | UUID format |

**Example:**

```
DELETE http://localhost:8080/api/teammates?id=123e4567-e89b-42d3-a456-556642440000

```

Delete a teammate 


## 🛠️ Technologies Used

- Java 21
- Spring Boot
- Docker & Docker Compose
- Postman & Newman
- Swagger UI
---

## 🌟 Author

**Victor (victor220686)**


