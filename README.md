# Microservices with Spring Boot and Kafka

Event-driven microservices architecture demonstrating inter-service communication using Apache Kafka.

## Architecture

- **Order Service**: REST API for order management, publishes events to Kafka
- **Notification Service**: Consumes order events and processes notifications
- **Kafka**: Message broker for asynchronous communication
- **Docker**: Containerized deployment

## Tech Stack

- Java 21
- Spring Boot 3.2
- Apache Kafka
- Spring Data JPA / H2 Database
- Docker & Docker Compose
- Maven

## Features

✅ RESTful APIs with proper HTTP status codes  
✅ Event-driven architecture for loose coupling  
✅ Asynchronous messaging via Kafka  
✅ Transaction management with Spring @Transactional  
✅ Dockerized microservices  
✅ Clean architecture (Controller → Service → Repository)

## Running Locally

1. Start Kafka:
```bash
docker-compose up -d
```

2. Run Order Service (port 8081)
3. Run Notification Service (port 8082)

## API Endpoints

### Create Order
```
POST http://localhost:8081/api/orders
Content-Type: application/json

{
  "customerName": "John Doe",
  "productName": "Laptop",
  "quantity": 1,
  "totalPrice": 1500.00
}
```

### Get All Orders
```
GET http://localhost:8081/api/orders
```

## Architecture Decisions

- **Kafka over REST**: Asynchronous communication prevents cascading failures
- **Event-driven**: Services are loosely coupled, can scale independently
- **Docker**: Consistent environments from dev to production

## Author

Built as part of microservices learning journey.
```
