FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/*.jar matchApiService.jar

ENTRYPOINT ["java", "-jar", "matchApiService.jar"]