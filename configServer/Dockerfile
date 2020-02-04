FROM openjdk:8-jre-alpine
VOLUME /tmp
COPY target/demo-0.0.1-SNAPSHOT.jar ConfigServer.jar/
ENTRYPOINT ["java","-jar","ConfigServer.jar"]