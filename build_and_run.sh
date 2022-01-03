#!/bin/bash

# Build all
./gradlew build

# Build docker images
./gradlew template:jibDockerBuild
./gradlew notification:jibDockerBuild
./gradlew verification:jibDockerBuild

# Run
docker-compose up