@ECHO OFF

DEL C:\Data\FinancialSample.pdf

SET DOCKER_BUILDKIT=0
docker build -t test-debian --no-cache --file Dockerfile-Debian .

docker run --mount type=bind,source=C:\Data,target=/data test-debian
