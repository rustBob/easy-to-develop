-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: milk_tea
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `add_ins`
--

DROP TABLE IF EXISTS `add_ins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `add_ins` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `price` decimal(10,0) NOT NULL COMMENT '价格',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改sj',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小料';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `add_ins`
--

LOCK TABLES `add_ins` WRITE;
/*!40000 ALTER TABLE `add_ins` DISABLE KEYS */;
INSERT INTO `add_ins` VALUES (1,'珍珠',1,'ttttttt','2025-12-22 15:40:33','2025-12-22 15:40:33');
/*!40000 ALTER TABLE `add_ins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banners`
--

DROP TABLE IF EXISTS `banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banners` (
  `id` varchar(20) NOT NULL COMMENT 'id',
  `img` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='轮播图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
INSERT INTO `banners` VALUES ('1452747914244784128','测试1');
/*!40000 ALTER TABLE `banners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `key` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'人气必喝','hot'),(2,'鲜果茶','fresh_fruit'),(3,'纯茶','pure_tea'),(4,'咖啡','coffee');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` varchar(20) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT '类型',
  `value` decimal(10,0) NOT NULL DEFAULT '1' COMMENT '优惠额度',
  `name` varchar(100) NOT NULL DEFAULT '2592000' COMMENT '名称',
  `min_amount` int DEFAULT NULL COMMENT '最小使用金额',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `coupon_discount_strategy_code_fk` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES ('1452771595687821312','cash',5,'膨胀神券',10,'2025-12-22 21:15:39','2025-12-22 21:15:39');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cup_size`
--

DROP TABLE IF EXISTS `cup_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cup_size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `key` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='杯量';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cup_size`
--

LOCK TABLES `cup_size` WRITE;
/*!40000 ALTER TABLE `cup_size` DISABLE KEYS */;
/*!40000 ALTER TABLE `cup_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drinks`
--

DROP TABLE IF EXISTS `drinks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drinks` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '饮品ID',
  `category_id` int DEFAULT NULL,
  `name` varchar(50) NOT NULL COMMENT '饮品名称',
  `price` decimal(10,0) NOT NULL COMMENT '价格',
  `img` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `drinks_categories_id_fk` (`category_id`),
  CONSTRAINT `drinks_categories_id_fk` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='饮品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drinks`
--

LOCK TABLES `drinks` WRITE;
/*!40000 ALTER TABLE `drinks` DISABLE KEYS */;
INSERT INTO `drinks` VALUES (1,1,'珍珠奶茶',9,NULL,'ttttttt','2025-12-22 15:18:24','2025-12-22 15:18:24');
/*!40000 ALTER TABLE `drinks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_card`
--

DROP TABLE IF EXISTS `member_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_card` (
  `id` int NOT NULL COMMENT '会员卡类型ID',
  `member_level` int NOT NULL COMMENT '会员等级',
  `card_name` varchar(20) NOT NULL COMMENT '会员卡等级名',
  `key` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_card`
--

LOCK TABLES `member_card` WRITE;
/*!40000 ALTER TABLE `member_card` DISABLE KEYS */;
INSERT INTO `member_card` VALUES (1,1,'普通会员','normal_member'),(2,2,'青铜会员','bronze'),(3,3,'白银会员','silver'),(4,4,'黄金会员','gold');
/*!40000 ALTER TABLE `member_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` varchar(20) NOT NULL,
  `user_id` varchar(20) NOT NULL COMMENT '用户ID',
  `drink_id` int NOT NULL COMMENT '饮品id',
  `drinks_amount` int NOT NULL COMMENT '数量',
  `add_ins_id` int DEFAULT NULL COMMENT '小料',
  `add_ins_amount` int DEFAULT NULL COMMENT '小料数量',
  `temperature_id` int NOT NULL COMMENT '温度',
  `cup_size_id` int NOT NULL COMMENT '杯量',
  `sugar_percent` int NOT NULL COMMENT '甜度',
  `total_price` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orders_user_id_fk` (`user_id`),
  KEY `orders_drinks_id_fk` (`drink_id`),
  KEY `orders_add_ins_id_fk` (`add_ins_id`),
  KEY `orders_cup_size_id_fk` (`cup_size_id`),
  KEY `orders_temperature_id_fk` (`temperature_id`),
  KEY `orders_sugar_percent_id_fk` (`sugar_percent`),
  CONSTRAINT `orders_add_ins_id_fk` FOREIGN KEY (`add_ins_id`) REFERENCES `add_ins` (`id`),
  CONSTRAINT `orders_cup_size_id_fk` FOREIGN KEY (`cup_size_id`) REFERENCES `cup_size` (`id`),
  CONSTRAINT `orders_drinks_id_fk` FOREIGN KEY (`drink_id`) REFERENCES `drinks` (`id`),
  CONSTRAINT `orders_sugar_percent_id_fk` FOREIGN KEY (`sugar_percent`) REFERENCES `sugar_percent` (`id`),
  CONSTRAINT `orders_temperature_id_fk` FOREIGN KEY (`temperature_id`) REFERENCES `temperature` (`id`),
  CONSTRAINT `orders_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL COMMENT '角色id',
  `parent_id` bigint NOT NULL COMMENT '父角色id',
  `name` varchar(100) NOT NULL COMMENT '角色名',
  `key` varchar(100) NOT NULL COMMENT '角色权限标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,1,'用户','user'),(2,1,'管理员','admin');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sugar_percent`
--

DROP TABLE IF EXISTS `sugar_percent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sugar_percent` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `key` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='甜度';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugar_percent`
--

LOCK TABLES `sugar_percent` WRITE;
/*!40000 ALTER TABLE `sugar_percent` DISABLE KEYS */;
/*!40000 ALTER TABLE `sugar_percent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temperature`
--

DROP TABLE IF EXISTS `temperature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `temperature` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(20) NOT NULL,
  `key` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='温度';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temperature`
--

LOCK TABLES `temperature` WRITE;
/*!40000 ALTER TABLE `temperature` DISABLE KEYS */;
/*!40000 ALTER TABLE `temperature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` varchar(20) NOT NULL,
  `order_id` varchar(20) NOT NULL COMMENT '订单ID',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `coupon_id` varchar(20) DEFAULT NULL COMMENT '优惠券ID',
  `total_price` decimal(10,0) NOT NULL COMMENT '总价',
  `real_price` decimal(10,0) NOT NULL COMMENT '最终价格',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `date_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `transaction_user_id_fk` (`user_id`),
  KEY `transaction_orders_id_fk` (`order_id`),
  KEY `transaction_coupon_id_fk` (`coupon_id`),
  CONSTRAINT `transaction_coupon_id_fk` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`),
  CONSTRAINT `transaction_orders_id_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `transaction_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='流水';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(20) NOT NULL,
  `role_id` varchar(20) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `member_card_id` int NOT NULL DEFAULT '1' COMMENT '会员卡等级id',
  `total_points` int NOT NULL DEFAULT '0' COMMENT '总积分',
  `available_points` int NOT NULL DEFAULT '0' COMMENT '可用积分',
  `balance` int NOT NULL DEFAULT '0' COMMENT '余额',
  `is_enabled` int NOT NULL DEFAULT '1' COMMENT '是否启用',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1452407845071880192','2','admin','1jzzblqPR3mTyEUMtGdFXg==',NULL,NULL,NULL,NULL,1,0,0,0,1,0,'2025-12-21 21:10:14','2025-12-21 21:10:14');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_coupon`
--

DROP TABLE IF EXISTS `user_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_coupon` (
  `id` varchar(20) NOT NULL COMMENT 'id',
  `user_id` varchar(20) NOT NULL COMMENT '用户ID',
  `coupon_id` varchar(20) NOT NULL COMMENT '优惠券ID',
  `is_valid` int NOT NULL DEFAULT '1' COMMENT '是否有效',
  `expiration` int NOT NULL DEFAULT '2592000' COMMENT '有效期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_coupon_user_id_fk` (`user_id`),
  KEY `user_coupon_coupon_id_fk` (`coupon_id`),
  CONSTRAINT `user_coupon_coupon_id_fk` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`),
  CONSTRAINT `user_coupon_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户优惠券关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_coupon`
--

LOCK TABLES `user_coupon` WRITE;
/*!40000 ALTER TABLE `user_coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_coupon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-22 21:22:23
