@ECHO OFF

DEL C:\Data\FinancialSample.pdf

SET DOCKER_BUILDKIT=0
docker build -t test-lambda2 --no-cache --file Dockerfile-Lambda2 .

docker run --mount type=bind,source=C:\Data,target=/data test-lambda2
