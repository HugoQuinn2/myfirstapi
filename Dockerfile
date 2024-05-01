FROM openjdk:22

COPY target/*.jar java-app.jar
ENTRYPOINT ["java", "-jar", "java-app.jar"]
