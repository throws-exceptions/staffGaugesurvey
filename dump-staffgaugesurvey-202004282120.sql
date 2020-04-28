-- MariaDB dump 10.17  Distrib 10.4.12-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: staffgaugesurvey
-- ------------------------------------------------------
-- Server version	10.4.12-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargo` (
  `cargo_id` int(11) NOT NULL AUTO_INCREMENT,
  `freighters_num` varchar(255) NOT NULL,
  `start_weight` float NOT NULL,
  `end_weight` float NOT NULL,
  `goods_weight` float NOT NULL,
  `person` varchar(30) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`cargo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_todolist`
--

DROP TABLE IF EXISTS `tb_todolist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_todolist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `event_date` date NOT NULL,
  `event_title` varchar(100) NOT NULL,
  `event_desc` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_todolist`
--

LOCK TABLES `tb_todolist` WRITE;
/*!40000 ALTER TABLE `tb_todolist` DISABLE KEYS */;
INSERT INTO `tb_todolist` VALUES (7,'傅杰青','2020-04-14','测试日历成功','测试成功'),(8,'傅杰青','2020-04-15','完成用户资料编写','包括用户资料头像上传，用户更改用户，验证邮箱并更改'),(9,'傅杰青','2020-04-18','测试用户资料更新成功',''),(10,'傅杰青','2020-04-18','修复bug','修复以下bug\n1、文件上传之后直接关闭对话框\n2、用户的资料不是实时更新、dashboard的create方法\n');
/*!40000 ALTER TABLE `tb_todolist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `head_img_url` varchar(255) NOT NULL,
  `salt` varchar(30) NOT NULL,
  `phone_number` varchar(11) DEFAULT '',
  `permission` varchar(1) NOT NULL,
  `mail` varchar(30) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'傅杰青2','D6C40A5D8C8CA743ACD5E636F728A141','http://images.nowcoder.com/head/800t.png','50d01','123456','B',''),(2,'傅杰青','95DCF6189083E601B308FC87D4A73E88','20200418131313timelapse-photography-off-water-fountain-719396.jpg','fbd8a','123456','A','1163107972@qq.com'),(3,'fu','B5CF9EEF266CF4FD35536C4150923FF9','http://images.nowcoder.com/head/667t.png','9cbaa','123456','B','113asdas'),(4,'fujie','8BE5805D9443276A5E86A8CBEF2386DF','http://images.nowcoder.com/head/67t.png','4490a','123456','B',''),(7,'admin','4ABA9F8A06F36AF5D590E96B50C75614','http://images.nowcoder.com/head/786t.png','ba7c5','123456','A','1163107972@qq.com');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_code`
--

DROP TABLE IF EXISTS `tb_user_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_code` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) NOT NULL,
  `code` varchar(6) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_code`
--

LOCK TABLES `tb_user_code` WRITE;
/*!40000 ALTER TABLE `tb_user_code` DISABLE KEYS */;
INSERT INTO `tb_user_code` VALUES (1,'傅杰青','540515'),(2,'admin','191216');
/*!40000 ALTER TABLE `tb_user_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'staffgaugesurvey'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-28 21:20:22
