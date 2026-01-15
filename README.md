# Behavioral Design Patterns - Order Processing System

## Patterns Implemented

### Chain of Responsibility
Order validation chain:
- `InventoryCheckHandler` - validates inventory
- `PaymentValidationHandler` - validates payment

### Command
- `PlaceOrderCommand` - executes order placement

### Observer
- `NotificationService` - manages observers
- `EmailNotification` - email notifications
- `SMSNotification` - SMS notifications

### Strategy
- `CreditCardPayment` - credit card payment processing

## Project Structure

```
src/main/java/org/example/
├── OrderApplication.java
├── Order.java
├── controller/
│   └── OrderController.java
├── service/
│   └── OrderService.java
├── repository/
│   └── OrderRepository.java
├── handler/
│   ├── OrderValidationHandler.java
│   ├── InventoryCheckHandler.java
│   └── PaymentValidationHandler.java
├── command/
│   ├── OrderCommand.java
│   └── PlaceOrderCommand.java
├── notification/
│   ├── Observer.java
│   ├── NotificationService.java
│   ├── EmailNotification.java
│   └── SMSNotification.java
└── payment/
    ├── PaymentStrategy.java
    └── CreditCardPayment.java
```

## Requirements

- Java 17 (Amazon Corretto)
- Maven

## Run

```bash
mvn spring-boot:run
```

## Access

- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:orderdb`
  - Username: `sa`
  - Password: `password`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders` | Place new order |
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get order by ID |
