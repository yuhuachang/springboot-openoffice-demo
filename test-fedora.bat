@ECHO OFF

DEL C:\Data\FinancialSample.pdf

SET DOCKER_BUILDKIT=0
docker build -t test-fedora --no-cache --file Dockerfile-Fedora .

docker run --mount type=bind,source=C:\Data,target=/data test-fedora
