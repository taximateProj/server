# OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk-slim

# 애플리케이션 JAR 파일의 위치를 변수로 정의
ARG JAR_FILE=build/libs/*SNAPSHOT.jar

# 컨테이너 내 작업 디렉토리 설정
WORKDIR /app

# 로컬의 JAR 파일을 컨테이너의 작업 디렉토리로 복사
COPY ${JAR_FILE} app.jar

# 컨테이너 시작 시 실행할 명령어 설정
ENTRYPOINT ["java", "-jar", "app.jar"]