FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY /src/main/resources/keystore/apiEncryptionKey.jks /keystore/apiEncryptionKey.jks
COPY /src/main/resources/keystore/publicKey.jks /keystore/publicKey.jks
COPY /src/main/resources/keystore/trustServer.cer /keystore/trustServer.cer
COPY build/libs/configservice-1.0.jar ConfigServer.jar
ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]