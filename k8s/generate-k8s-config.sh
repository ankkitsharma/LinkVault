#!/bin/bash
# Script to generate Kubernetes ConfigMap and Secret from .env file

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_FILE="${SCRIPT_DIR}/.env"

if [ ! -f "$ENV_FILE" ]; then
  echo "Error: .env file not found in $SCRIPT_DIR"
  echo "Please copy .env.example to .env and update the values"
  exit 1
fi

# Source the .env file
source "$ENV_FILE"

# Generate ConfigMap
cat > "${SCRIPT_DIR}/configmap.yaml" << CONFIGMAP_EOF
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
  labels:
    app: bookmark
data:
  # Database Configuration
  POSTGRES_DB: "${POSTGRES_DB:-bookmarkdb}"
  POSTGRES_HOST: "postgres"
  POSTGRES_PORT: "5432"
  
  # Backend Configuration
  SPRING_PROFILES_ACTIVE: "${SPRING_PROFILES_ACTIVE:-docker}"
  SERVER_PORT: "${SERVER_PORT:-8080}"
  SPRING_DATASOURCE_URL: "${SPRING_DATASOURCE_URL:-jdbc:postgresql://postgres:5432/${POSTGRES_DB:-bookmarkdb}}"
  
  # Frontend Configuration
  VITE_API_URL: "${VITE_API_URL:-http://bookmarks.local/api}"
  
  # Ingress Configuration
  INGRESS_HOST: "${INGRESS_HOST:-bookmarks.local}"
  
  # Resource Configuration
  BACKEND_REPLICAS: "${BACKEND_REPLICAS:-2}"
  FRONTEND_REPLICAS: "${FRONTEND_REPLICAS:-2}"
CONFIGMAP_EOF

# Generate Secret (using base64 encoding for production)
USERNAME_B64=$(echo -n "${POSTGRES_USER:-bookmarkuser}" | base64)
PASSWORD_B64=$(echo -n "${POSTGRES_PASSWORD:-bookmarkpass}" | base64)

cat > "${SCRIPT_DIR}/postgres-secret.yaml" << SECRET_EOF
apiVersion: v1
kind: Secret
metadata:
  name: postgres-secret
  labels:
    app: bookmark
type: Opaque
# Base64 encoded values (recommended for production)
data:
  username: ${USERNAME_B64}
  password: ${PASSWORD_B64}
SECRET_EOF

echo "✓ Generated configmap.yaml from .env"
echo "✓ Generated postgres-secret.yaml from .env"
echo ""
echo "You can now apply these files:"
echo "  kubectl apply -f ${SCRIPT_DIR}/configmap.yaml"
echo "  kubectl apply -f ${SCRIPT_DIR}/postgres-secret.yaml"
