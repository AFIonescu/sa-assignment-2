# E-Commerce Order Processing System

Spring Boot application demonstrating 4 Behavioral Design Patterns with H2 database persistence.

## Design Patterns Implemented

### 1. Chain of Responsibility
Order validation pipeline with three handlers:
- **CustomerValidationHandler** - Validates customer name and email
- **InventoryCheckHandler** - Checks product availability and quantity
- **PaymentValidationHandler** - Validates payment details

### 2. Strategy Pattern
Multiple payment processing strategies:
- **CreditCardPayment** - Credit card processing
- **PayPalPayment** - PayPal account processing
- **CryptocurrencyPayment** - Cryptocurrency wallet processing

### 3. Observer Pattern
Notification system for order status changes:
- **EmailNotification** - Sends email notifications
- **SMSNotification** - Sends SMS notifications

### 4. Command Pattern
Encapsulated order operations:
- **PlaceOrderCommand** - Places a new order
- **CancelOrderCommand** - Cancels an existing order

## Tech Stack

- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- H2 Database (In-memory)
- Lombok 1.18.34
- Maven 3.6+
- JUnit 5 & Mockito

## Prerequisites

- Java 17
- Maven 3.6+

## Build & Run

### Build
```bash
cd Assignment-2
.\build.bat
```

Expected output: `BUILD SUCCESS` with 20 tests passing.

### Run
```bash
.\run.bat
```

Application starts on `http://localhost:8080`

## Testing

### API Endpoints

**Place Order (Credit Card)**
```bash
curl -X POST http://localhost:8080/api/orders -H "Content-Type: application/json" -d "{\"customerName\":\"John Doe\",\"customerEmail\":\"john@example.com\",\"productName\":\"Laptop\",\"quantity\":1,\"totalAmount\":1000.00,\"paymentMethod\":\"CREDIT_CARD\",\"paymentDetails\":\"1234567890123456\"}"
```

Expected response:
```json
{
  "id": 1,
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "productName": "Laptop",
  "quantity": 1,
  "totalAmount": 1000.0,
  "status": "CONFIRMED",
  "paymentMethod": "CREDIT_CARD",
  "createdAt": "2025-12-28T05:00:00.472772",
  "updatedAt": "2025-12-28T05:00:00.489475"
}
```

**Place Order (PayPal)**
```bash
curl -X POST http://localhost:8080/api/orders -H "Content-Type: application/json" -d "{\"customerName\":\"Alice Smith\",\"customerEmail\":\"alice@example.com\",\"productName\":\"Phone\",\"quantity\":2,\"totalAmount\":1500.00,\"paymentMethod\":\"PAYPAL\",\"paymentDetails\":\"alice@paypal.com\"}"
```

**Get All Orders**
```bash
curl http://localhost:8080/api/orders
```

**Cancel Order**
```bash
curl -X DELETE http://localhost:8080/api/orders/1
```

Expected response: Order with `"status": "CANCELLED"`

### H2 Database Console

Access: `http://localhost:8080/h2-console`

**Connection Settings:**
- JDBC URL: `jdbc:h2:mem:ecommerce`
- Username: `sa`
- Password: (empty)

**Query Orders:**
```sql
SELECT * FROM ORDERS;
```

Expected: Table showing all orders with ID, customer info, product, amount, status, payment method, and timestamps.

### Run Tests
```bash
.\run.bat test
```

Expected output: `Tests run: 20, Failures: 0, Errors: 0, Skipped: 0`

