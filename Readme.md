# Steps to build & run this Java application in a docker container

1. Run this command in the root folder "ReceiptProcessor\" to build the docker container <br />
`docker build -f Dockerfile -t receipt-points-processor.jar .`

2. Run the application by starting the docker container. <br />
`docker run -p 8080:8080 receipt-points-processor.jar`

3. Execute POST and GET requests on http://localhost:8080