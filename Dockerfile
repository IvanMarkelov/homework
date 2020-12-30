FROM openjdk:8-jre

COPY lesson18/src lesson18/src
COPY lesson18/target/classes lesson18/target/classes

EXPOSE 8080

CMD ["java", "-cp", "lesson18/target/classes", "DockerServer"]
