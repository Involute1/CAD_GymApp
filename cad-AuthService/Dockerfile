FROM maven:latest AS builder

# Copy and build backend
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests=true

FROM openjdk:17-bullseye

# Get wait-for-it
RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
RUN chmod +x wait-for-it.sh

# Command to run on standalone container execution
COPY --from=builder /app/target/*.jar AuthService.jar
ENTRYPOINT ["java", "-jar", "/AuthService.jar"]
