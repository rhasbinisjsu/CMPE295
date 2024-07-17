## Run the cropsense-application-server container
docker run -d -p 8000:8080 --name cropsense-application-server --network cropsense-network cropsense-application-server:0.0.1-SNAPSHOT