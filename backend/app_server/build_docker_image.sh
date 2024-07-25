## Build the docker image
docker build -t cropsense-application-server:0.0.1-SNAPSHOT-LOCAL .

docker buildx build -t rhasbinisjsu/cropsense-application-server:0.0.1-SNAPSHOT . --platform=linux/amd64,linux/arm64 --push
