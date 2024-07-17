## Run the metrics-server container
docker run -d -p 8001:8080 --name cropsense-metrics-server --network cropsense-network cropsense-metrics-server:0.0.1-SNAPSHOT