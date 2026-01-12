# 基础镜像
FROM eclipse-temurin:17-jre

# 设置容器工作目录
WORKDIR /product

# 拷贝 jar 到容器里，并改名为 product.jar
COPY product-0.0.1-SNAPSHOT.jar product.jar

# 容器启动命令
ENTRYPOINT ["java","-jar","product.jar"]
