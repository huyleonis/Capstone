-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: ats
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `id` varchar(50) NOT NULL,
  `stationId` int(11) NOT NULL,
  `createdTime` datetime NOT NULL,
  `status` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `laneId` int(11) DEFAULT NULL,
  `type` tinyint(1) DEFAULT '0',
  `photo` varchar(45) DEFAULT NULL,
  `vehicleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transaction_station_idx` (`stationId`),
  KEY `fk_transaction_price_idx` (`price`),
  KEY `fk_transaction_lane_idx` (`laneId`),
  KEY `fk_transaction_vehicle_idx` (`vehicleId`),
  CONSTRAINT `fk_transaction_lane` FOREIGN KEY (`laneId`) REFERENCES `lane` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_station` FOREIGN KEY (`stationId`) REFERENCES `station` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_vehicle` FOREIGN KEY (`vehicleId`) REFERENCES `vehicle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES ('1510500714155',1,'2017-11-12 22:31:54','Failed',20000,NULL,1,NULL,3),('1510538075722',1,'2017-11-13 09:50:36','Success',10000,NULL,0,'99K9999_1510538075722.jpg',1),('1510540186148',1,'2017-11-13 10:25:46','Not pay',10000,NULL,0,'99K9999_1510540186148.jpg',1),('1510540258045',1,'2017-11-15 08:35:58','Not pay',50000,NULL,0,'62S3333_1510540258045.jpg',7),('1510540259276',1,'2017-11-13 11:20:59','Not pay',45000,NULL,0,'52S3333_1510540259276.jpg',6),('1510544879899',1,'2017-11-13 11:20:00','Not pay',10000,NULL,0,'99K9999_1510544879899.jpg',1),('1510709672362',1,'2017-11-15 08:34:32','Finish',10000,NULL,2,NULL,1),('1510709673252',1,'2017-11-15 08:34:33','Not pay',10000,NULL,2,NULL,1),('1510710081948',1,'2017-11-15 08:41:22','Finish',10000,NULL,2,NULL,1),('1510710082104',1,'2017-11-15 08:41:22','Not pay',10000,NULL,2,NULL,1),('1510710446316',1,'2017-11-15 08:47:26','Finish',50000,NULL,2,NULL,7),('1510710446613',1,'2017-11-15 08:47:27','Not pay',50000,NULL,2,NULL,7),('1510710603662',1,'2017-11-15 08:50:04','Finish',10000,NULL,2,NULL,1),('1510710603740',1,'2017-11-15 08:50:04','Not pay',10000,NULL,2,NULL,1),('1510712190992',1,'2017-11-15 09:16:31','Success',10000,NULL,1,NULL,1),('1510712387483',1,'2017-11-15 09:19:47','Success',10000,NULL,1,NULL,1),('1510713650913',1,'2017-11-15 09:40:51','Initial',40000,NULL,0,'30S4593_1510713650913.jpg',5),('1510713664801',1,'2017-11-15 09:41:05','Failed',40000,NULL,1,NULL,5),('1510713862972',1,'2017-11-15 09:44:23','Success',40000,NULL,1,NULL,5),('1510713919486',1,'2017-11-15 09:45:19','Initial',40000,NULL,0,'30S4593_1510713919486.jpg',5);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-17 10:09:18