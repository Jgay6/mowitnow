FROM openjdk:21-jdk-slim
LABEL name="mowitnow"
LABEL author="Jeremy GAY"

COPY target/mowitnow-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java" , "-jar" , "/app.jar"]
