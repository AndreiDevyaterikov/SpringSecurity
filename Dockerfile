FROM gradle:8.3.0-jdk17 as builder
WORKDIR /app
COPY . /app
RUN gradle clean build -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/*.jar
EXPOSE 8182
ENTRYPOINT ["java", "-jar", "/app/*.jar"]