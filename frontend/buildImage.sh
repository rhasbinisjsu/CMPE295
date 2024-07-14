# Build and tag the docker image locally
docker build -t cropsense-ui:SNAPSHOT-0.0.1 .

# Build and tag the docker image for multi-platform compatability
# Push the docker image to the dockerhub image repository
#docker buildx build -t cropsense-ui:SNAPSHOT-0.0.1 . --platform=linux/arm64,linux/amd64 --push
