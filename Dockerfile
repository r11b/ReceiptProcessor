FROM openjdk:17

ADD target/receipt-points-0.0.1-SNAPSHOT.jar receipt-points.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "receipt-points.jar"]