FROM eclipse-temurin:17-jdk-focal
WORKDIR /metrics-server
COPY target/metrics_server-0.0.1-SNAPSHOT.jar .
ENV CROPSENSE_APPLICATION_SERVER_HOST=44.204.8.116
ENV CROPSENSE_APPLICATION_SERVER_PORT=8000
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "metrics_server-0.0.1-SNAPSHOT.jar" ]
