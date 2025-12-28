# 前端构建阶段
FROM node:20-alpine AS build

# 设置前端构建目录
WORKDIR /app

# 进入包管理目录
COPY package*.json ./

# 安装前端项目依赖
RUN npm install

# 复制前端项目源代码
COPY . .

# 构建前端项目
RUN npm run build

# 运行时阶段 nginx
FROM nginx:alpine

# 复制前端项目构建结果到 nginx 目录
COPY --from=build /app/dist /usr/share/nginx/html

# 复制 nginx 配置文件
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 健康检查
#  检查 nginx 是否正常运行
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost || exit 1

# 暴露端口
EXPOSE 80

# 启动 nginx 服务
CMD ["nginx", "-g", "daemon off;"]
