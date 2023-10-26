FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/ploggingservice-1.0.jar PloggingServer.jar
ENTRYPOINT ["java", "-jar", "PloggingServer.jar"]