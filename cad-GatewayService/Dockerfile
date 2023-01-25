FROM maven:latest AS builder

# Copy and build backend
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests=true

FROM openjdk:17-bullseye


# Command to run on standalone container execution
COPY --from=builder /app/target/*.jar GatewayService.jar
ENTRYPOINT ["java", "-jar", "/GatewayService.jar", "--spring.config.name=prod"]
