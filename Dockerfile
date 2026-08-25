# ===== Stage 1: Build WAR bằng Maven =====
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ===== Stage 2: Deploy WAR lên Tomcat =====
FROM tomcat:11.0-jdk17
# Xoá app mặc định của Tomcat để tránh xung đột đường dẫn "/"
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR vừa build ở stage 1 vào Tomcat, đổi tên thành ROOT.war
# để app chạy ở địa chỉ gốc "/" thay vì "/ten-project"
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Render.com sẽ tự set biến PORT, Tomcat mặc định chạy ở 8080
EXPOSE 8080

CMD ["catalina.sh", "run"]