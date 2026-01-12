# 基础镜像
FROM eclipse-temurin:17-jre

# 设置容器工作目录
WORKDIR /gateway

# 拷贝 jar 到容器里，并改名为 product.jar
COPY target/*.jar gateway.jar

# 容器启动命令
ENTRYPOINT ["java","-jar","gateway.jar"]
