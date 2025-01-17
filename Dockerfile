FROM openjdk:17

ADD target/receipt-points-processor.jar receipt-points-processor.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "receipt-points-processor.jar"]