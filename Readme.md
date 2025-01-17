# Steps to build & run docker container
docker build -f Dockerfile -t receipt-points-processor.jar .

docker run -p 8080:8080 receipt-points-processor.jar