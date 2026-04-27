# DR4W Marketplace

Modern marketplace API built with **Java 21**, **Spring Boot 3.3**, and **Hexagonal Architecture**.

## Tech stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 (Virtual Threads) |
| Framework | Spring Boot 3.3 |
| Security | Spring Security 6 + JJWT (stateless JWT) |
| Persistence | Spring Data JPA + PostgreSQL 16 |
| Migrations | Flyway |
| Docs | SpringDoc OpenAPI 3 |
| Tests | JUnit 5 + Testcontainers |
| Build | Maven 3.9 |

## Bounded contexts

- **Identity** — registration, authentication, JWT issuance
- **Catalog** — products, categories, stock, vendor listings
- **Cart** — per-user cart with item management
- **Order** — order placement, status tracking, stock decrement
- **Payment** — gateway integration, payment records
- **Wallet** — vendor balance tracking with transaction history

## Running locally

### Prerequisites

- Java 21+
- Docker (for PostgreSQL)

### Start the database

```bash
docker compose up -d postgres
```

### Run the application

```bash
./mvnw spring-boot:run
```

### API documentation

Once running, visit: `http://localhost:8080/swagger-ui.html`

### Run tests

```bash
./mvnw test
```

> Integration tests use Testcontainers — Docker must be running.

## Environment variables

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_HOST` | `localhost` | PostgreSQL host |
| `DB_PORT` | `5432` | PostgreSQL port |
| `DB_NAME` | `marketplace` | Database name |
| `DB_USER` | `marketplace` | Database user |
| `DB_PASSWORD` | `marketplace` | Database password |
| `JWT_SECRET` | (insecure default) | HMAC-SHA secret — **change in production** |
| `JWT_EXPIRATION_MS` | `900000` | Access token TTL (15 min) |
| `PLATFORM_FEE_PERCENTAGE` | `5` | Platform fee % deducted from vendor earnings |
| `SERVER_PORT` | `8080` | HTTP server port |

## API overview

```
POST   /api/v1/auth/register       Register (BUYER / VENDOR)
POST   /api/v1/auth/login          Login → JWT

GET    /api/v1/products            List products (public)
GET    /api/v1/products/{id}       Get product (public)
POST   /api/v1/products            Create product (VENDOR / ADMIN)

GET    /api/v1/cart                Get current cart
POST   /api/v1/cart/items          Add item to cart
DELETE /api/v1/cart/items/{pid}    Remove item from cart

POST   /api/v1/orders              Place order (checkout)
GET    /api/v1/orders              List my orders
GET    /api/v1/orders/{id}         Get order

GET    /api/v1/wallet              Get wallet balance + transactions
```

## Docker build

```bash
./mvnw clean package -DskipTests
docker build -t dr4w-marketplace .
docker run -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e JWT_SECRET=your-secret-here \
  dr4w-marketplace
```
