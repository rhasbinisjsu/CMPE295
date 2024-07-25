## Build the cropsense-metrics-server image
docker build -t cropsense-metrics-server:0.0.1-SNAPSHOT .

docker buildx build -t rhasbinisjsu/cropsense-metrics-server:0.0.1-SNAPSHOT . --platform=linux/amd64,linux/arm64 --push
