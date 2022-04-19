FROM openjdk:8-jdk-alpine
RUN apk add --update python3 py3-pip
RUN mkdir tinyCode
RUN mkdir target
COPY target target/
ENTRYPOINT ["java","-jar","/target/tinyCode-0.0.1-SNAPSHOT.jar"]
