-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cs_db
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `easy_admin_menu`
--

DROP TABLE IF EXISTS `easy_admin_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `easy_admin_menu` (
  `id` bigint unsigned NOT NULL COMMENT '菜单id',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父菜单id',
  `role_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '所属角色',
  `name` varchar(100) NOT NULL COMMENT '菜单名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `order` tinyint unsigned DEFAULT NULL COMMENT '显示顺序',
  `path` varchar(100) NOT NULL COMMENT '路由地址',
  `component` varchar(200) NOT NULL DEFAULT '#' COMMENT '组件路径',
  `is_visible` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否可见',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理端菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `easy_admin_menu`
--

LOCK TABLES `easy_admin_menu` WRITE;
/*!40000 ALTER TABLE `easy_admin_menu` DISABLE KEYS */;
INSERT INTO `easy_admin_menu` VALUES (1,0,0,'仪表盘','House',1,'dashboard','/admin/DashboardManager.vue',1),(350503859048693760,0,0,'用户管理','User',3,'users','/admin/user/UsersManager.vue',1),(350504359253000192,0,0,'菜单管理','Menu',2,'menus','/admin/MenuManager.vue',1),(350504785637556224,0,0,'系统设置','Setting',4,'settings','/SettingsManager.vue',0),(350726428418969600,0,0,'角色管理','Avatar',3,'roles','/admin/user/RolesManager.vue',1);
/*!40000 ALTER TABLE `easy_admin_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `easy_role`
--

DROP TABLE IF EXISTS `easy_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `easy_role` (
  `id` bigint NOT NULL COMMENT '角色id',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父角色id',
  `name` varchar(100) NOT NULL COMMENT '角色名',
  `key` varchar(100) NOT NULL COMMENT '角色权限标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `easy_role`
--

LOCK TABLES `easy_role` WRITE;
/*!40000 ALTER TABLE `easy_role` DISABLE KEYS */;
INSERT INTO `easy_role` VALUES (350729423328186368,0,'超级管理员','super-admin'),(350729651649318912,350729423328186368,'管理员','admin'),(350729685119864832,350729651649318912,'普通用户','user');
/*!40000 ALTER TABLE `easy_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `easy_user`
--

DROP TABLE IF EXISTS `easy_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `easy_user` (
  `id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint unsigned NOT NULL COMMENT '角色id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `sex` tinyint NOT NULL DEFAULT (-(1)) COMMENT '性别（1为男，0为女，-1为未知)',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `is_enabled` int NOT NULL DEFAULT '1' COMMENT '是否启用',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `easy_user_pk_2` (`username`),
  UNIQUE KEY `easy_user_pk` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `easy_user`
--

LOCK TABLES `easy_user` WRITE;
/*!40000 ALTER TABLE `easy_user` DISABLE KEYS */;
INSERT INTO `easy_user` VALUES (1451620309294645248,350729423328186368,'admin','ouFuZRpLXaiTuzZeXOvhvA==',-1,NULL,1,0,'2025-12-19 09:00:51','2025-12-19 09:00:51');
/*!40000 ALTER TABLE `easy_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `easy_web_config`
--

DROP TABLE IF EXISTS `easy_web_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `easy_web_config` (
  `key` varchar(200) NOT NULL COMMENT '键',
  `value` varchar(200) NOT NULL COMMENT '值',
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='网页配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `easy_web_config`
--

LOCK TABLES `easy_web_config` WRITE;
/*!40000 ALTER TABLE `easy_web_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `easy_web_config` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-19 21:14:09
