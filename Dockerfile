FROM openjdk
RUN mkdir app
COPY /target/linkTest-0.0.1-SNAPSHOT.jar app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]