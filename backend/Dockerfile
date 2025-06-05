# 1. 빌드용 이미지 (멀티 스테이지 빌드)
FROM gradle:7.6.1-jdk17 AS builder

WORKDIR /app

COPY --chown=gradle:gradle . /app

RUN ./gradlew clean build --no-daemon -x test

# 2. 실행용 이미지
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
