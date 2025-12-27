# Bookmark Manager - Fullstack Application

A modern fullstack bookmark management application built with Spring Boot, React, TanStack Router, PostgreSQL, Docker, GitHub Actions, and Kubernetes.

## Tech Stack

### Backend
- **Spring Boot 3.2.0** - Java framework
- **PostgreSQL** - Database
- **Spring Data JPA** - Data persistence
- **Maven** - Build tool

### Frontend
- **React 18** - UI library
- **TanStack Router** - Routing
- **Vite** - Build tool
- **TypeScript** - Type safety

### Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Local development
- **Kubernetes** - Orchestration
- **GitHub Actions** - CI/CD

## Project Structure

```
spring-blog/
├── backend/          # Spring Boot application
├── frontend/         # React + Vite application
├── k8s/             # Kubernetes manifests
├── docker-compose.yml
└── README.md
```

## Getting Started

### Prerequisites

- Java 17+
- Node.js 20+
- Docker & Docker Compose
- Maven 3.6+

### Local Development

1. **Start PostgreSQL and services with Docker Compose:**
   ```bash
   docker-compose up -d
   ```

2. **Or run services individually:**

   **Backend:**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

   **Frontend:**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

3. **Access the application:**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080/api/bookmarks

### Docker Development

Build and run all services:
```bash
docker-compose up --build
```

### Kubernetes Deployment

1. **Apply secrets:**
   ```bash
   kubectl apply -f k8s/postgres-secret.yaml
   ```

2. **Deploy PostgreSQL:**
   ```bash
   kubectl apply -f k8s/postgres-deployment.yaml
   ```

3. **Deploy Backend:**
   ```bash
   kubectl apply -f k8s/backend-deployment.yaml
   ```

4. **Deploy Frontend:**
   ```bash
   kubectl apply -f k8s/frontend-deployment.yaml
   ```

5. **Deploy Ingress (optional):**
   ```bash
   kubectl apply -f k8s/ingress.yaml
   ```

**Note:** Make sure to build and push Docker images to your container registry before deploying to Kubernetes, or use a local registry.

## API Endpoints

- `GET /api/bookmarks` - Get all bookmarks
- `GET /api/bookmarks/{id}` - Get bookmark by ID
- `POST /api/bookmarks` - Create a new bookmark
- `PUT /api/bookmarks/{id}` - Update a bookmark
- `DELETE /api/bookmarks/{id}` - Delete a bookmark
- `GET /api/bookmarks/search?q={query}` - Search bookmarks

## Features

- ✅ Create, read, update, and delete bookmarks
- ✅ Search bookmarks by title
- ✅ Responsive UI
- ✅ Docker containerization
- ✅ Kubernetes deployment ready
- ✅ CI/CD with GitHub Actions

## Development

### Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Building for Production

### Backend
```bash
cd backend
mvn clean package
```

### Frontend
```bash
cd frontend
npm run build
```

## License

MIT

