FROM eclipse-temurin:17-jdk-focal
WORKDIR /application-server
COPY target/app_server-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENV CROPSENSE_RDB_URL=cropsense-rdb.cvko804qsp36.us-east-1.rds.amazonaws.com
ENV CROPSENSE_RDB_USER=admin
ENV CROPSENSE_RDB_PASSWORD=password
ENTRYPOINT [ "java", "-jar", "app_server-0.0.1-SNAPSHOT.jar" ]
