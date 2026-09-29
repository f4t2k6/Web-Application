FROM ubuntu:latest
LABEL authors="LeFat"

ENTRYPOINT ["top", "-b"]

# ---------- Giai đoạn 1: build WAR bằng Maven ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom trước để tận dụng cache khi tải dependency
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Giai đoạn 2: chạy trên Tomcat ----------
FROM tomcat:10.1-jdk21-temurin

# Xóa ứng dụng mặc định của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy WAR thành ROOT để truy cập ở đường dẫn gốc (/)
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Render cấp cổng qua biến PORT: đổi cổng Tomcat cho khớp rồi khởi động
CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-8080}\\\"/\" /usr/local/tomcat/conf/server.xml && catalina.sh run"]