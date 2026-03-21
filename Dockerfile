FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

# Give permission to mvnw
RUN chmod +x mvnw

# Build project
RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/*.jar"]