# Steps for docker
docker build -f Dockerfile -t receipt-points.jar .

docker run -p 8080:8080 receipt-points.jar