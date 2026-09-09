# ---------- Stage 1: Build WAR bằng Maven ----------
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml trước để tận dụng Docker layer cache cho dependency
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy toàn bộ source code còn lại và build
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Deploy lên Tomcat ----------
FROM tomcat:10.1-jdk17

# Xóa app mặc định của Tomcat (webapps/ROOT, docs, examples...) để tránh xung đột
RUN rm -rf /usr/local/tomcat/webapps/*


COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
