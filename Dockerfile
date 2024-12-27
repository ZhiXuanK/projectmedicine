FROM openjdk:23-jdk-oracle AS builder

WORKDIR /src

# copy files
COPY mvnw .
COPY pom.xml .

COPY .mvn .mvn
COPY src src

# make mvnw executable
RUN chmod a+x mvnw && /src/mvnw package -Dmaven.test.skip=true
# /src/target/noticeboard-0.0.1-SNAPSHOT.jar

# second stage
FROM openjdk:23-jdk-oracle

WORKDIR /app

COPY --from=builder /src/target/projectmedicine-0.0.1-SNAPSHOT.jar project.jar

ENV PORT=8080

EXPOSE ${PORT}

#HEALTHCHECK --interval=60s --start-period=120s CMD curl -s -f http://localhost:${PORT}/status || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar project.jar