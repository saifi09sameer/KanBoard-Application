# Stage 1: Build the Angular app
FROM node:14.17.0 as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Stage 2: Serve the Angular app using Nginx
FROM nginx:1.21.1-alpine
COPY --from=build-stage /app/dist/application /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
