FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/simon-rico-afterpaytest-0.0.1-SNAPSHOT.jar simon-rico-afterpaytest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/simon-rico-afterpaytest-0.0.1-SNAPSHOT.jar"]
