FROM openjdk:17-jdk-alpine
LABEL authors="adelaguila"

RUN apk --no-cache add maven

WORKDIR /app

COPY . .

RUN mvn dependency:resolve

RUN mvn clean package -DskipTest

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/example-cqrs-1.0-SNAPSHOT.jar"]