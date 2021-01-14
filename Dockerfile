FROM openjdk:11
VOLUME /tmp
EXPOSE 9100
ADD ./target/app-ws-exchange-rate-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]