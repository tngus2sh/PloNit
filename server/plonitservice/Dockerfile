FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/plonitservice-1.0.jar PlonitServer.jar
ENTRYPOINT ["java", "-jar", "PlonitServer.jar"]