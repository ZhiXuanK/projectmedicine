FROM openjdk:22-jdk AS builder

WORKDIR /src

# copy files
COPY mvnw .
COPY pom.xml .

COPY .mvn .mvn
COPY src src

# make mvnw executable
RUN chmod a+x mvnw && /src/mvnw package -Dmaven.test.skip=true

# second stage
FROM openjdk:22-jdk

WORKDIR /app

COPY --from=builder /src/target/projectmedicine-0.0.1-SNAPSHOT.jar project.jar

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar project.jar