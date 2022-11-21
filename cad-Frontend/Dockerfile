FROM node:alpine AS cad-Frontend
WORKDIR /src/app
COPY . .
RUN npm install && npm run build

FROM nginx:alpine
COPY --from=cad-Frontend src/app/dist/cad-frontend/ /usr/share/nginx/html
EXPOSE 80
