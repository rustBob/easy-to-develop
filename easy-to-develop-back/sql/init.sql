-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: mk_db
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `after_sale`
--

DROP TABLE IF EXISTS `after_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `after_sale` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `type` int NOT NULL COMMENT '1为申请退款，2为投诉建议',
  `order_id` bigint unsigned NOT NULL COMMENT '售后订单id',
  `description` text COLLATE utf8mb4_general_ci NOT NULL COMMENT '售后内容',
  `status` int NOT NULL DEFAULT '1' COMMENT '审批状态（1待审批，2同意、3拒绝）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `after_sale`
--

LOCK TABLES `after_sale` WRITE;
/*!40000 ALTER TABLE `after_sale` DISABLE KEYS */;
INSERT INTO `after_sale` VALUES (362416694456516608,1,1454229052268216320,'没有按照备注加珍珠',3);
/*!40000 ALTER TABLE `after_sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `after_sale_image`
--

DROP TABLE IF EXISTS `after_sale_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `after_sale_image` (
  `id` bigint unsigned NOT NULL,
  `after_sale_id` bigint unsigned NOT NULL,
  `image` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `after_sale_image`
--

LOCK TABLES `after_sale_image` WRITE;
/*!40000 ALTER TABLE `after_sale_image` DISABLE KEYS */;
INSERT INTO `after_sale_image` VALUES (362412643509665792,362412642012299264,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766817565197-aftersales.png'),(362416573807362048,362416572414853120,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766818501290-aftersales.png'),(362416695471538176,362416694456516608,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766818531830-aftersales.png');
/*!40000 ALTER TABLE `after_sale_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banners`
--

DROP TABLE IF EXISTS `banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banners` (
  `id` bigint unsigned NOT NULL COMMENT 'id',
  `img` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='轮播图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
INSERT INTO `banners` VALUES (361420111371857920,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1556679343-c7306c1976bc.avif'),(361420211682832384,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1497534446932-c925b458314e.avif'),(362152815227027456,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/6000.jpg_wh860.jpg');
/*!40000 ALTER TABLE `banners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint unsigned NOT NULL COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `key` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'人气必喝','hot'),(2,'鲜果茶','fresh_fruit'),(3,'纯茶','pure_tea'),(4,'咖啡','coffee'),(362136804675272704,'测试分类',NULL);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` bigint unsigned NOT NULL,
  `type` varchar(20) NOT NULL COMMENT '类型',
  `value` decimal(10,1) NOT NULL DEFAULT '1.0' COMMENT '优惠额度',
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
INSERT INTO `coupon` VALUES (362148419361554432,'DISCOUNT',0.9,'折扣券',0,'2025-12-26 21:09:31','2025-12-26 22:02:20'),(1452771595687821312,'cash',5.0,'膨胀神券',10,'2025-12-22 21:15:39','2025-12-22 21:15:39');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drinks`
--

DROP TABLE IF EXISTS `drinks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drinks` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '饮品ID',
  `category_id` bigint unsigned DEFAULT NULL,
  `name` varchar(50) NOT NULL COMMENT '饮品名称',
  `price` decimal(10,0) NOT NULL COMMENT '价格',
  `image` varchar(500) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `drinks_categories_id_fk` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=362147447801364481 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='饮品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drinks`
--

LOCK TABLES `drinks` WRITE;
/*!40000 ALTER TABLE `drinks` DISABLE KEYS */;
INSERT INTO `drinks` VALUES (1,1,'珍珠奶茶',12,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP.webp','经典奶茶，回味无穷','2025-12-22 15:18:24','2025-12-24 16:25:32'),(361352584079884288,2,'满杯红柚',22,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1546173159-315724a31696.avif','满满维C，清爽解腻','2025-12-24 16:27:09','2025-12-25 20:15:11'),(361352902188482560,3,'高山四季春',12,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1627435601361-ec25f5b1d0e5.jpg','清新回甘，高山原叶','2025-12-24 16:28:25','2025-12-25 20:15:33'),(361353443350167552,4,'香纯拿铁',16,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP%20%281%29.webp','慢慢香纯','2025-12-24 16:30:34','2025-12-25 20:15:58'),(361619551185633280,2,'可乐柠檬茶',16,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/photo-1556679343-c7306c1976bc.avif','爽口可乐柠檬茶','2025-12-25 10:07:57','2025-12-25 20:15:38'),(361619738729742336,2,'草莓果茶',16,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/photo-1497534446932-c925b458314e.avif','甜甜可口','2025-12-25 10:08:42','2025-12-25 20:16:02'),(362147447801364480,362136804675272704,'测试商品',12,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/6000.jpg_wh860.jpg','测试商品','2025-12-26 21:05:39','2025-12-26 21:05:39');
/*!40000 ALTER TABLE `drinks` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `easy_admin_menu` VALUES (1,0,0,'仪表盘','House',1,'dashboard','/admin/DashboardManager.vue',1),(350503859048693760,360902064114552832,361446649408532480,'用户管理','User',3,'users','/admin/user/UsersManager.vue',1),(350504359253000192,0,361446649408532480,'菜单管理','Menu',2,'menus','/admin/MenuManager.vue',1),(350504785637556224,0,361446649408532480,'系统设置','Setting',4,'settings','/SettingsManager.vue',0),(350726428418969600,360902064114552832,361446649408532480,'角色管理','Avatar',3,'roles','/admin/user/RolesManager.vue',1),(360711075752153088,0,2,'商品','Dessert',4,'goods','#',1),(360717069022498816,360711075752153088,361446649408532480,'分类管理','KnifeFork',4,'categories','/admin/good/CategoriesManager.vue',1),(360717331523014656,360711075752153088,361446649408532480,'商品管理','Coffee',1,'milkyTeas','/admin/good/MilkyTeasManager.vue',1),(360724322358259712,360711075752153088,361446649408532480,'优惠券管理','Money',2,'coupons','/admin/good/CouponsManager.vue',1),(360885147656028160,360711075752153088,361446649408532480,'规格管理','EditPen',3,'specs','/admin/good/SpecsManager.vue',1),(360886553330876416,0,361446649408532480,'活动管理','Flag',6,'activities','#',1),(360886720650051584,360886553330876416,361446649408532480,'轮播图管理','DataAnalysis',1,'banners','/admin/activity/BannersManager.vue',1),(360890148570824704,360902064114552832,361446649408532480,'会员等级','UserFilled',4,'members','/admin/user/MembersManager.vue',1),(360894569690222592,0,2,'订单','Edit',7,'orders','#',1),(360894808018964480,360894569690222592,2,'订单管理','Notebook',1,'orders','/admin/order/OrdersManager.vue',1),(360902064114552832,0,361446649408532480,'用户','User',2,'users','#',1),(360904148306153472,0,361446649408532480,'门店管理','MapLocation',3,'stores','/admin/store/StoresManager.vue',1),(361713313568067584,360711075752153088,2,'门店商品管理','IceDrink',2,'store-goods','/admin/good/StoreGoodsManager.vue',1),(362405689315885056,360894569690222592,2,'售后管理','Edit',1,'after-sale','/admin/order/AfterSalesManager.vue',1);
/*!40000 ALTER TABLE `easy_admin_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `easy_file`
--

DROP TABLE IF EXISTS `easy_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `easy_file` (
  `id` bigint unsigned NOT NULL COMMENT '文件id',
  `user_id` bigint unsigned NOT NULL COMMENT '上传人员id',
  `url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件路径',
  `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `ext_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件后缀类型',
  `size` int NOT NULL COMMENT '文件大小',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '上传状态0未成功1成功',
  `is_public` int NOT NULL DEFAULT '1' COMMENT '是否公开可见',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文件类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `easy_file`
--

LOCK TABLES `easy_file` WRITE;
/*!40000 ALTER TABLE `easy_file` DISABLE KEYS */;
INSERT INTO `easy_file` VALUES (1453408773711331328,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766561253619-avatar.png','1766561253619-avatar','png',1321,'2025-12-24 15:27:35',0,1),(1453418768163143680,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/553df9b3ef85e13faf02a5dcd6b2f3ea.jpg','553df9b3ef85e13faf02a5dcd6b2f3ea','jpg',119374,'2025-12-24 16:07:17',0,1),(1453419128076369920,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/553df9b3ef85e13faf02a5dcd6b2f3ea.jpg','553df9b3ef85e13faf02a5dcd6b2f3ea','jpg',119374,'2025-12-24 16:08:43',0,1),(1453419448319868928,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/553df9b3ef85e13faf02a5dcd6b2f3ea.jpg','553df9b3ef85e13faf02a5dcd6b2f3ea','jpg',119374,'2025-12-24 16:09:59',0,1),(1453423350771613696,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP.webp','OIP','webp',7602,'2025-12-24 16:25:30',1,1),(1453423687993655296,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1546173159-315724a31696.avif','photo-1546173159-315724a31696','avif',13912,'2025-12-24 16:26:50',1,1),(1453423985214619648,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1627435601361-ec25f5b1d0e5.jpg','photo-1627435601361-ec25f5b1d0e5','jpg',8795,'2025-12-24 16:28:01',1,1),(1453424520252620800,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP%20%281%29.webp','OIP (1)','webp',16320,'2025-12-24 16:30:08',1,1),(1453490752960069632,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1556679343-c7306c1976bc.avif','photo-1556679343-c7306c1976bc','avif',72701,'2025-12-24 20:53:20',1,1),(1453491098151288832,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1556679343-c7306c1976bc.avif','photo-1556679343-c7306c1976bc','avif',72701,'2025-12-24 20:54:42',1,1),(1453491357896146944,1452777732571136000,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/photo-1497534446932-c925b458314e.avif','photo-1497534446932-c925b458314e','avif',53148,'2025-12-24 20:55:44',1,1),(1453690295852990464,1452407845071880192,NULL,'photo-1556679343-c7306c1976bc','avif',72701,'2025-12-25 10:06:13',0,1),(1453690840164597760,1452407845071880192,NULL,'photo-1497534446932-c925b458314e','avif',53148,'2025-12-25 10:08:22',0,1),(1454208032874430464,1452407845071880192,NULL,'6000.jpg_wh860','jpg',124703,'2025-12-26 20:23:33',0,1),(1454208190458626048,1452407845071880192,NULL,'6000.jpg_wh860','jpg',124703,'2025-12-26 20:24:10',0,1),(1454217632512212992,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/6000.jpg_wh860.jpg','6000.jpg_wh860','jpg',124703,'2025-12-26 21:01:41',1,1),(1454217995378229248,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/6000.jpg_wh860.jpg','6000.jpg_wh860','jpg',124703,'2025-12-26 21:03:08',1,1),(1454218118774652928,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/6000.jpg_wh860.jpg','6000.jpg_wh860','jpg',124703,'2025-12-26 21:03:37',1,1),(1454218589060988928,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/6000.jpg_wh860.jpg','6000.jpg_wh860','jpg',124703,'2025-12-26 21:05:29',1,1),(1454223987952320512,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/6000.jpg_wh860.jpg','6000.jpg_wh860','jpg',124703,'2025-12-26 21:26:56',1,1),(1454479224776687616,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766816470074-aftersales.png','1766816470074-aftersales','png',1321,'2025-12-27 14:21:10',1,1),(1454481472642613248,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766817005988-aftersales.png','1766817005988-aftersales','png',1321,'2025-12-27 14:30:05',1,1),(1454483818248404992,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766817565197-aftersales.png','1766817565197-aftersales','png',1321,'2025-12-27 14:39:25',1,1),(1454487744423133184,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766818501290-aftersales.png','1766818501290-aftersales','png',1321,'2025-12-27 14:55:01',1,1),(1454487872378765312,1452407845071880192,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766818531830-aftersales.png','1766818531830-aftersales','png',1321,'2025-12-27 14:55:31',1,1);
/*!40000 ALTER TABLE `easy_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_specs`
--

DROP TABLE IF EXISTS `good_specs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_specs` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `good_id` bigint unsigned NOT NULL COMMENT '商品id',
  `specs_id` bigint unsigned NOT NULL COMMENT '规格id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品规格表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_specs`
--

LOCK TABLES `good_specs` WRITE;
/*!40000 ALTER TABLE `good_specs` DISABLE KEYS */;
INSERT INTO `good_specs` VALUES (1,1,1452968544722485248),(361772367954841600,361352584079884288,1452968544722485248),(361772368546238464,361352584079884288,1452968807550156800),(361772460674125824,361352902188482560,1452968544722485248),(361772482752942080,361619551185633280,1452968544722485248),(361772483285618688,361619551185633280,1452968807550156800),(361772566571913216,361353443350167552,1452968544722485248),(361772567087812608,361353443350167552,1452968807550156800),(361772583244271616,361619738729742336,1452968544722485248),(362147447826530304,362147447801364480,1452968544722485248),(362147447843307520,362147447801364480,1452968807550156800);
/*!40000 ALTER TABLE `good_specs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `phone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `user_id` bigint unsigned NOT NULL,
  `position` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '位置',
  `detail` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细地址',
  `tag` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址类型',
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `gender` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户地址';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (361431811907239936,'12312341234',1452407845071880192,'贵州大学','博学楼','学校','小王',0),(362163960453251072,'12312341234',1452407845071880192,'贵州大学','博学楼','学校','大鼠',0);
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_card`
--

DROP TABLE IF EXISTS `member_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_card` (
  `id` bigint unsigned NOT NULL COMMENT '会员卡类型ID',
  `member_level` int NOT NULL COMMENT '会员等级',
  `card_name` varchar(20) NOT NULL COMMENT '会员卡等级名',
  `discount` decimal(10,1) NOT NULL COMMENT '折扣力度',
  `key` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_card`
--

LOCK TABLES `member_card` WRITE;
/*!40000 ALTER TABLE `member_card` DISABLE KEYS */;
INSERT INTO `member_card` VALUES (1,0,'普通会员',1.0,'normal_member'),(4,1,'黄金会员',0.9,'gold'),(361423044951961600,2,'钻石会员',0.8,NULL);
/*!40000 ALTER TABLE `member_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint unsigned NOT NULL,
  `order_id` bigint unsigned NOT NULL COMMENT '所属订单',
  `drink_id` bigint unsigned NOT NULL COMMENT '饮品id',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `image` varchar(500) NOT NULL COMMENT '商品图片',
  `description` varchar(500) NOT NULL COMMENT '商品描述',
  `drinks_quantity` bigint unsigned NOT NULL COMMENT '数量',
  `price` decimal(10,1) NOT NULL,
  `specs` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单商品详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (361353443350167552,1453880427101028352,361353443350167552,'香纯拿铁','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP%20%281%29.webp','慢慢香纯',1,16.0,'大杯/常温'),(361707755669360640,361434708035026944,1,'珍珠奶茶','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP.webp','经典奶茶，回味无穷',1,12.0,'麻辣加醋'),(361731470754926592,1453802654433017856,1,'珍珠奶茶','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP.webp','经典奶茶，回味无穷',1,12.0,'麻辣加醋'),(1453884722429034497,1453884722429034496,1,'珍珠奶茶','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP.webp','经典奶茶，回味无穷',1,12.0,'大杯'),(1454229052268216321,1454229052268216320,361619551185633280,'可乐柠檬茶','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/photo-1556679343-c7306c1976bc.avif','爽口可乐柠檬茶',1,16.0,'大杯/常温'),(1454416800971227137,1454416800971227136,1,'珍珠奶茶','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP.webp','经典奶茶，回味无穷',1,12.0,'大杯'),(1454416800988004352,1454416800971227136,362147447801364480,'测试商品','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/6000.jpg_wh860.jpg','测试商品',1,12.0,'大杯/加热'),(1454416862862376961,1454416862862376960,1,'珍珠奶茶','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452777732571136000/OIP.webp','经典奶茶，回味无穷',1,12.0,'大杯/加热'),(1454416862879154176,1454416862862376960,361619551185633280,'可乐柠檬茶','https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/photo-1556679343-c7306c1976bc.avif','爽口可乐柠檬茶',1,16.0,'大杯/常温');
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint unsigned NOT NULL COMMENT 'is',
  `order_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '下单类型',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `store_id` bigint unsigned NOT NULL COMMENT '商店ID',
  `coupon_id` bigint unsigned DEFAULT NULL,
  `location_id` bigint unsigned DEFAULT NULL COMMENT '地址ID',
  `estimated_time` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '预计所需时间',
  `status` int NOT NULL DEFAULT '1' COMMENT '订单状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pickup_code` int NOT NULL COMMENT '取餐码',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总价',
  `final_amount` decimal(10,2) NOT NULL COMMENT '最终价格',
  `coupon_discount_amount` decimal(10,2) NOT NULL COMMENT '优惠券减免金额',
  `level_discount_amount` decimal(10,2) NOT NULL COMMENT '会员减免金额',
  `points_deduction` decimal(10,2) NOT NULL COMMENT '积分抵扣金额',
  `points_consumption` int DEFAULT NULL,
  `remarks` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (361434708035026944,'pickup',1452407845071880192,1,NULL,NULL,'900',4,'2025-12-24 21:53:29',1477,22.00,21.00,0.00,0.00,1.00,10,'少冰，奶味重一点'),(1453802654433017856,'send-out',1452777732571136000,1,NULL,361431811907239936,'900',4,'2025-12-25 17:32:44',5782,0.00,0.00,0.00,0.00,0.00,0,'666ewfef是否是'),(1453880427101028352,'pickup',1452407845071880192,1,NULL,0,'1766675506022',5,'2025-12-25 22:41:43',6883,16.00,10.00,5.00,0.00,1.04,104,'少冰'),(1453884722429034496,'delivery',1452407845071880192,361763867627368448,NULL,361431811907239936,'1766676530136',4,'2025-12-25 22:58:47',7577,12.00,12.00,5.00,0.00,0.00,0,''),(1454229052268216320,'pickup',1452407845071880192,1,NULL,0,'1766758624341',4,'2025-12-26 21:47:04',8021,16.00,16.00,0.00,0.00,0.00,0,''),(1454416800971227136,'pickup',1452407845071880192,1,NULL,0,'1766803387124',2,'2025-12-27 10:13:07',2933,24.00,21.54,2.30,0.00,0.16,16,'少糖'),(1454416862862376960,'delivery',1452407845071880192,1,NULL,361431811907239936,'1766803401878',2,'2025-12-27 10:13:21',3412,28.00,28.00,5.00,0.00,0.00,0,'少糖');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint unsigned NOT NULL COMMENT '角色id',
  `parent_id` bigint unsigned NOT NULL COMMENT '父角色id',
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
INSERT INTO `role` VALUES (1,2,'用户','user'),(2,361446649408532480,'门店管理员','admin'),(361446649408532480,0,'超级管理员','super-admin');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specs`
--

DROP TABLE IF EXISTS `specs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specs` (
  `id` bigint unsigned NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `option` varchar(200) DEFAULT NULL COMMENT '选项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='饮品选项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specs`
--

LOCK TABLES `specs` WRITE;
/*!40000 ALTER TABLE `specs` DISABLE KEYS */;
INSERT INTO `specs` VALUES (362152222236176384,'加料','[\"加珍珠\",\"加糖\"]'),(1452968544722485248,'杯量','[\"大杯\",\"中杯\",\"小杯\"]'),(1452968807550156800,'温度','[\"常温\",\"少冰\",\"加冰\",\"加热\"]');
/*!40000 ALTER TABLE `specs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `id` bigint unsigned NOT NULL,
  `user_id` bigint unsigned NOT NULL COMMENT '管理员id',
  `name` varchar(50) NOT NULL COMMENT '门店名称',
  `address` varchar(50) NOT NULL COMMENT '地址',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `status` varchar(20) NOT NULL COMMENT '营业状态',
  `hours` varchar(20) NOT NULL COMMENT '营业时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `store_pk` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='门店';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (1,1452777543596769280,'云顶奶茶 (科技园店)','南山区科技园南区W1-B栋','0755-000-000','营业中','10:00-22:00'),(361763867627368448,1452777732571136000,'云顶奶茶 (万象天地店)','南山区深南大道9668号','12312341234','营业中','10:00-20:00'),(362154741045903360,1454149548606750720,'云顶奶茶（万科路店）','万科路37号','0851-1234','营业中','10：00-22：00');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_good`
--

DROP TABLE IF EXISTS `store_good`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_good` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `store_id` bigint unsigned NOT NULL COMMENT '门店id',
  `good_id` bigint unsigned NOT NULL COMMENT '商品id',
  `is_sale` int NOT NULL DEFAULT '0' COMMENT '是否启售',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='门店商品关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_good`
--

LOCK TABLES `store_good` WRITE;
/*!40000 ALTER TABLE `store_good` DISABLE KEYS */;
INSERT INTO `store_good` VALUES (361724713044586496,1452976130838495232,361352902188482560,1),(361724794787377152,1452976130838495232,361353443350167552,0),(361763985306955776,361763867627368448,1,1),(361764117901488128,1,361353443350167552,1),(362058529454014464,1,1,1),(362058545987960832,1,361619551185633280,1),(362058562115059712,1,361352902188482560,1),(362156243747586048,1,362147447801364480,1);
/*!40000 ALTER TABLE `store_good` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint unsigned NOT NULL,
  `order_id` bigint unsigned NOT NULL COMMENT '订单ID',
  `user_id` bigint unsigned DEFAULT NULL COMMENT '用户ID',
  `coupon_id` bigint unsigned DEFAULT NULL COMMENT '优惠券ID',
  `total_price` decimal(10,0) NOT NULL COMMENT '总价',
  `real_price` decimal(10,0) NOT NULL COMMENT '最终价格',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `date_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `transaction_user_id_fk` (`user_id`),
  KEY `transaction_orders_id_fk` (`order_id`),
  KEY `transaction_coupon_id_fk` (`coupon_id`)
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
  `id` bigint unsigned NOT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `nickname` varchar(40) NOT NULL COMMENT '昵称',
  `password` varchar(50) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `member_card_id` int NOT NULL DEFAULT '1' COMMENT '会员卡等级id',
  `total_points` int NOT NULL DEFAULT '0' COMMENT '总积分',
  `balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
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
INSERT INTO `user` VALUES (1452407845071880192,361446649408532480,'https://blog-1369198252.cos.ap-chengdu.myqcloud.com/1452407845071880192/1766561253619-avatar.png','admin','admin','Exj2JlXowfj+vlc2Z2rt5w==','',1,NULL,1,49,44.92,1,0,'2025-12-21 21:10:14','2025-12-27 10:13:21'),(1452777543596769280,2,NULL,'test','test','xH/NJFN6NqpPE3Uw01XKhg==',NULL,1,NULL,1,0,0.00,1,0,'2025-12-22 21:39:18','2025-12-26 15:14:23'),(1452777732571136000,2,NULL,'test1','test1','18xHFSAbHScQq1fOgPk+KA==',NULL,1,NULL,1,0,0.00,1,0,'2025-12-22 21:40:03','2025-12-22 22:13:24'),(1454149548606750720,1,NULL,'1234','奶茶达人','xrnKyICrKDRE1fQA3S8WgQ==',NULL,NULL,NULL,1,0,0.00,1,0,'2025-12-26 16:31:04','2025-12-26 16:31:04'),(1454150590262149120,1,NULL,'271034','爱喝奶茶','zApjID5ycvFABKD68+ZUHA==',NULL,NULL,NULL,1,0,0.00,1,1,'2025-12-26 16:35:13','2025-12-26 16:35:13'),(1454429254040158208,1,NULL,'271034','爱喝爱茶','tVj3Ubl/0umLJByG486WEA==',NULL,NULL,NULL,1,0,0.00,1,1,'2025-12-27 11:02:36','2025-12-27 11:02:36');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_coupon`
--

DROP TABLE IF EXISTS `user_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_coupon` (
  `id` bigint unsigned NOT NULL COMMENT 'id',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `coupon_id` bigint unsigned NOT NULL COMMENT '优惠券ID',
  `is_valid` int NOT NULL DEFAULT '1',
  `expiration` int NOT NULL DEFAULT '2592000' COMMENT '有效期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_coupon_user_id_fk` (`user_id`),
  KEY `user_coupon_coupon_id_fk` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户优惠券关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_coupon`
--

LOCK TABLES `user_coupon` WRITE;
/*!40000 ALTER TABLE `user_coupon` DISABLE KEYS */;
INSERT INTO `user_coupon` VALUES (361400710866173952,1452777543596769280,1452771595687821312,0,2592000,'2025-12-24 19:38:23'),(362135293442375680,1452407845071880192,1452771595687821312,1,2592000,'2025-12-26 20:17:22'),(362153604028809216,1452407845071880192,362148419361554432,1,2592000,'2025-12-26 21:30:07');
/*!40000 ALTER TABLE `user_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'mk_db'
--

--
-- Dumping routines for database 'mk_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-27 15:31:19
