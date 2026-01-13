# 基础镜像
FROM eclipse-temurin:17-jre

# 设置容器工作目录
WORKDIR /app

# 拷贝 jar 到容器里，并改名为 auth.jar
COPY auth-0.0.1-SNAPSHOT.jar auth.jar

# 容器启动命令
ENTRYPOINT ["java","-jar","auth.jar", "--spring.profiles.active=docker"]
