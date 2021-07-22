@ECHO OFF

DEL C:\Data\FinancialSample.pdf

SET DOCKER_BUILDKIT=0
docker build -t test-openjdk --no-cache --file Dockerfile-OpenJDK .

docker run --mount type=bind,source=C:\Data,target=/data test-openjdk
