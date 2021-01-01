FROM maven:3.6.3-jdk-8 as build_tool

ADD /lesson18/src /lesson18/src
ADD /lesson18/pom.xml /lesson18/pom.xml

RUN mvn -f lesson18/pom.xml package

FROM openjdk:8-jre

COPY --from=build_tool lesson18/target/classes/ lesson18/target/classes/

EXPOSE 8080

CMD ["java", "-cp", "lesson18/target/classes", "DockerServer"]
