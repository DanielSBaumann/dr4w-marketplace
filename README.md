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
| Build | Maven 3.9+ |

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
- Maven 3.9+ (`mvn -version` to check)
- Docker (for PostgreSQL)

> **Note:** this project has no Maven wrapper (`mvnw`). Use the system `mvn` command directly.

---

### 1. Start the database

```bash
cd /path/to/dr4w-marketplace
docker compose up -d postgres
```

Wait a few seconds for Postgres to be ready before starting the app.

---

### 2. Build the application

**You must build before the first run, and again after any code change:**

```bash
mvn clean package -DskipTests
```

`-DskipTests` keeps it fast (~15–20 s). Run without it to include integration tests (requires Docker running).

The output jar is placed at `target/marketplace-1.0.0-SNAPSHOT.jar`.

---

### 3. Start the application

```bash
java -jar target/marketplace-1.0.0-SNAPSHOT.jar
```

The app starts on `http://localhost:8080`. On first boot it runs Flyway migrations and seeds demo data automatically:

| Account | Email | Password | Role |
|---------|-------|----------|------|
| Vendor | `store@dr4w.io` | `Store@12345!` | VENDOR |
| Buyer | `buyer@dr4w.io` | `Buyer@12345!` | BUYER |

---

### When do I need to rebuild?

| Situation | Action needed |
|-----------|--------------|
| First time cloning the repo | `mvn clean package -DskipTests` then `java -jar ...` |
| After changing any Java source file | `mvn clean package -DskipTests` then restart |
| After changing only `application.yml` | Restart only (`java -jar ...`) |
| After changing a Flyway migration | `mvn clean package -DskipTests` then restart |
| Pulling new commits from remote | `mvn clean package -DskipTests` then restart |

---

### API documentation (Swagger UI)

Once running: `http://localhost:8080/swagger-ui.html`

---

### Run tests

```bash
mvn test
```

Integration tests use Testcontainers — Docker must be running.

---

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

Override any variable on the command line:

```bash
java -DJWT_SECRET=my-secret -jar target/marketplace-1.0.0-SNAPSHOT.jar
```

---

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

---

## Docker build

```bash
mvn clean package -DskipTests
docker build -t dr4w-marketplace .
docker run -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e JWT_SECRET=your-secret-here \
  dr4w-marketplace
```
