FROM openjdk:12-alpine as builder
WORKDIR /app
COPY . /app
ENV GRADLE_USER_HOME "/app/.gradle"
RUN ./gradlew build bootJar -x test --info

FROM openjdk:12-alpine as prod
WORKDIR /app
COPY --from=builder /app/build/libs/* .
RUN ls | head -n 1 | xargs -I@ mv @ app.jar
CMD java -jar app.jar

