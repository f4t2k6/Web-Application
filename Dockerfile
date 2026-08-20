FROM tomcat:10.1-jdk17

# Xóa các app mẫu có sẵn của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR đã build vào Tomcat, đổi tên thành ROOT.war
# để app chạy ở đường dẫn gốc "/" thay vì "/webapplication"
COPY target/webapplication.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]