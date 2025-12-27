FROM node:20-alpine AS build
WORKDIR /app

# Copy shared package to parent directory to match package.json path (file:../shared)
COPY shared /shared

# Copy frontend package files
COPY frontend/package.json frontend/package-lock.json* ./
RUN npm ci

# Copy frontend source code
COPY frontend/ .

# Build the application
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY --from=build /app/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

