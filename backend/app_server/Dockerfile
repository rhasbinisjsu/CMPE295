FROM eclipse-temurin:17-jdk-focal
WORKDIR /application-server
COPY target/app_server-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app_server-0.0.1-SNAPSHOT.jar" ]
