#FROM openjdk:11
FROM adoptopenjdk/maven-openjdk11:latest
VOLUME /tmp
EXPOSE 8081
ADD ./target/app-ws-exchange-rate-0.0.1-SNAPSHOT.jar app-ws-exchange-rate.jar
ENTRYPOINT ["java","-jar","/app-ws-exchange-rate.jar"]