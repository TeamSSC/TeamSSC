# Java 17 이미지를 사용하기
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정하기
WORKDIR /app

# 빌드된 JAR 파일을 이미지로 복사하기
COPY build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
