# Kubernetes Deployment Guide

## Prerequisites

You need a Kubernetes cluster running. Here are options for local development:

### Option 1: Docker Desktop (Easiest)

1. Open Docker Desktop
2. Go to Settings → Kubernetes
3. Enable Kubernetes
4. Click "Apply & Restart"

### Option 2: Minikube

```bash
# Install minikube (macOS)
brew install minikube

# Start minikube
minikube start

# Verify
kubectl get nodes
```

### Option 3: Kind (Kubernetes in Docker)

```bash
# Install kind
brew install kind

# Create cluster
kind create cluster --name bookmark-cluster

# Verify
kubectl get nodes
```

## Building Docker Images

Before deploying to Kubernetes, you need to build and push your Docker images to a registry, or load them into your local cluster:

### For Minikube:

```bash
# Set Docker environment to use minikube's Docker
eval $(minikube docker-env)

# Build images
cd backend && docker build -t bookmark-backend:latest .
cd ../frontend && docker build -t bookmark-frontend:latest .
```

### For Kind:

```bash
# Build images
cd backend && docker build -t bookmark-backend:latest .
cd ../frontend && docker build -t bookmark-frontend:latest .

# Load into kind
kind load docker-image bookmark-backend:latest --name bookmark-cluster
kind load docker-image bookmark-frontend:latest --name bookmark-cluster
```

### For Docker Desktop:

You can use local images directly, or push to a registry like Docker Hub.

## Deployment Steps

1. **Apply secrets:**

   ```bash
   kubectl apply -f k8s/postgres-secret.yaml
   ```

2. **Deploy PostgreSQL:**

   ```bash
   kubectl apply -f k8s/postgres-deployment.yaml
   ```

3. **Wait for PostgreSQL to be ready:**

   ```bash
   kubectl wait --for=condition=ready pod -l app=postgres --timeout=300s
   ```

4. **Deploy Backend:**

   ```bash
   kubectl apply -f k8s/backend-deployment.yaml
   ```

5. **Deploy Frontend:**

   ```bash
   kubectl apply -f k8s/frontend-deployment.yaml
   ```

6. **Deploy Ingress (optional):**
   ```bash
   kubectl apply -f k8s/ingress.yaml
   ```

## Verify Deployment

```bash
# Check all pods
kubectl get pods

# Check services
kubectl get services

# Check logs
kubectl logs -l app=backend
kubectl logs -l app=frontend
```

## Access the Application

### With LoadBalancer (Docker Desktop):

```bash
# Get the external IP
kubectl get service frontend

# Access at http://<EXTERNAL-IP>
```

### With Port Forwarding:

```bash
# Forward frontend port
kubectl port-forward service/frontend 3000:80

# Access at http://localhost:3000
```

### With Ingress:

If you have an ingress controller installed (like nginx-ingress), you can access via the ingress hostname configured in `ingress.yaml`.

## Troubleshooting

### If validation errors occur:

```bash
# Skip validation (not recommended, but useful for testing)
kubectl apply -f k8s/postgres-secret.yaml --validate=false
```

### Check pod status:

```bash
kubectl describe pod <pod-name>
```

### View pod logs:

```bash
kubectl logs <pod-name>
```
