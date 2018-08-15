FROM openjdk:8-jdk-alpine

ADD target/docker-acme.jar /docker-acme.jar
RUN sh -c 'touch /docker-acme.jar'
ENTRYPOINT ["java","-Dspring.profiles.active=local", "-Djava.security.egd=file:/dev/./urandom","-jar","/docker-acme.jar"]
