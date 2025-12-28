# maven 3.9 + openjdk 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

# 设置后端打包目录
WORKDIR /app

COPY pom.xml .

# 缓存后端依赖
RUN mvn dependency:go-offline -B

# 复制源代码
COPY src ./src

# 打包
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

# 设置后端运行目录
WORKDIR /app

# 复制项目jar包到后端运行目录
COPY --from=build /app/target/*.jar app.jar

# 环境变量
ENV SPRING_PROFILES_ACTIVE=local
ENV TZ=Asia/Shanghai

# 暴露端口
EXPOSE 8080

# 启动java服务
ENTRYPOINT ["java", "-jar", "app.jar"]
