FROM eclipse-temurin:21

LABEL maintainer="pauldjoums@gmail.com"

WORKDIR /app

COPY target/finance-0.0.1-SNAPSHOT.jar /app/finance.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "finance.jar"]

