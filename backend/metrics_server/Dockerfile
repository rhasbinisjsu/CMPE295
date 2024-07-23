FROM eclipse-temurin:17-jdk-focal
WORKDIR /metrics-server
COPY target/metrics_server-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "metrics_server-0.0.1-SNAPSHOT.jar" ]