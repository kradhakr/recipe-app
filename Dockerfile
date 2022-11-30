#
# Build stage
#
FROM maven:3-jdk-11
WORKDIR /app
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install


#
# Package stage
#
FROM openjdk:11-jdk
VOLUME /tmp
COPY target/recipe-app-0.0.1-SNAPSHOT.jar recipe-app.jar
ENTRYPOINT ["java","-jar","/recipe-app.jar"]
