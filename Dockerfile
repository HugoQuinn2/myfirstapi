FROM openjdk:22

COPY target/myfirtsapi-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT ["java", "-jar", "java-app.jar"]
