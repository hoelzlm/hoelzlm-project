# hoelzlm-project

Full-stack trainee project for managing package shipments.

The application lets users:

- create new shipments between predefined cities,
- choose a freight type (AIR or SEA),
- see an estimated delivery time before submitting,
- browse existing shipments in an overview page.

## Project Overview

This repository is split into two main applications:

- `backend/` contains a Spring Boot REST API.
- `frontend/` contains an Angular + Angular Material UI.

The frontend calls the backend directly on `http://localhost:8080`.

## How It Works

1. The backend seeds a default list of cities on startup if the city table is empty.
2. The frontend loads cities and offers a form to create shipments.
3. The frontend requests a calculated delivery estimate from the backend based on:
   - origin city,
   - destination city,
   - freight type.
4. On submit, the backend validates input, computes estimated delivery days, stores the shipment, and returns the created entity.
5. The overview page fetches and displays all shipments.

## Backend Structure

Backend root: `backend/backend/`

- `src/main/java/de/hoelzlem/backend/BackendApplication.java`
  - Spring Boot entry point.
- `src/main/java/de/hoelzlem/backend/DataLoader.java`
  - Seeds default cities on startup.
- `src/main/java/de/hoelzlem/backend/controllers/`
  - `CityController` exposes `GET /api/v1/cities`.
  - `ShipmentController` exposes shipment endpoints.
- `src/main/java/de/hoelzlem/backend/services/`
  - `ShipmentService` handles shipment creation and validation.
  - `DeliveryTimeService` contains delivery-time calculation logic.
- `src/main/java/de/hoelzlem/backend/entities/`
  - JPA entities for `City`, `Shipment`, `FreightType`, `ShipmentStatus`.
- `src/main/java/de/hoelzlem/backend/repositories/`
  - Spring Data repositories for database access.
- `src/main/resources/application.properties`
  - Database and JPA configuration.

### Backend API

- `GET /api/v1/cities`
  - Returns all available cities.
- `GET /api/v1/shipments`
  - Returns all shipments.
- `GET /api/v1/shipments/calculated?originCityId=...&destinationCityId=...&freightType=AIR|SEA`
  - Returns estimated delivery time in days.
- `POST /api/v1/shipments`
  - Creates a new shipment from payload:

```json
{
  "originCityId": 1,
  "destinationCityId": 2,
  "freightType": "AIR"
}
```

## Frontend Structure

Frontend root: `frontend/`

- `src/app/app.routes.ts`
  - Route configuration:
    - `/overview` shipment list
    - `/send` shipment form
- `src/app/app.html`
  - Main shell with toolbar, side navigation, and router outlet.
- `src/app/send-package/`
  - Shipment creation form and estimated delivery preview.
- `src/app/shipment-overview/`
  - Shipment list UI.
- `src/app/shipment.ts`
  - API service for shipment-related backend calls.

## Local Development

### Prerequisites

- Java 25
- Maven (or use `mvnw`/`mvnw.cmd`)
- Node.js + npm
- PostgreSQL

### 1) Configure Database

The backend currently expects:

- URL: `jdbc:postgresql://localhost:5432/shipping_db`
- Username: `local`
- Password: `1234567890`

Create the database and update credentials in `backend/backend/src/main/resources/application.properties` if needed.

### 2) Start Backend

From `backend/backend/`:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

### 3) Start Frontend

From `frontend/`:

```bash
npm install
npm run start
```

Open `http://localhost:4200`.

## Repository Layout

```text
.
|-- backend/
|   |-- backend/
|   |   |-- pom.xml
|   |   `-- src/
|   |       |-- main/java/de/hoelzlem/backend/
|   |       |   |-- controllers/
|   |       |   |-- services/
|   |       |   |-- repositories/
|   |       |   `-- entities/
|   |       `-- main/resources/application.properties
|-- database/
`-- frontend/
  |-- package.json
  `-- src/app/
    |-- send-package/
    `-- shipment-overview/
```
