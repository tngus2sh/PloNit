FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/apigatewayservice-1.0.jar ApigatewayServer.jar
ENTRYPOINT ["java", "-jar", "ApigatewayServer.jar"]