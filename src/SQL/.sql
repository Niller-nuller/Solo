-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: loccsql
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `playerinventory`
--

DROP TABLE IF EXISTS `playerinventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playerinventory` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `player_id` int NOT NULL,
                                   `item_id` int NOT NULL,
                                   `quantity` int DEFAULT '1',
                                   `slot_index` int NOT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `player_id` (`player_id`),
                                   KEY `item_id` (`item_id`),
                                   CONSTRAINT `playerinventory_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`),
                                   CONSTRAINT `playerinventory_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `worlditems` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playerinventory`
--

LOCK TABLES `playerinventory` WRITE;
/*!40000 ALTER TABLE `playerinventory` DISABLE KEYS */;
INSERT INTO `playerinventory` VALUES (1,1,4,1,0),(2,1,7,1,1),(3,1,17,3,2);
/*!40000 ALTER TABLE `playerinventory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-15 14:33:26


-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: loccsql
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(100) NOT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES (1,'Johnny'),(2,'test');
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-15 14:33:26


-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: loccsql
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `worlditems`
--

DROP TABLE IF EXISTS `worlditems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worlditems` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(100) NOT NULL,
                              `type` enum('Weapon','Armor','Consumable') NOT NULL,
                              `weight` decimal(5,2) NOT NULL,
                              `damage` int DEFAULT NULL,
                              `weapon_type` varchar(50) DEFAULT NULL,
                              `armor_type` varchar(50) DEFAULT NULL,
                              `is_stackable` tinyint(1) DEFAULT '0',
                              `stack_size` int DEFAULT '1',
                              `consumable_type` varchar(50) DEFAULT NULL,
                              `resistance` int DEFAULT '0',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worlditems`
--

LOCK TABLES `worlditems` WRITE;
/*!40000 ALTER TABLE `worlditems` DISABLE KEYS */;
INSERT INTO `worlditems` VALUES (1,'Short Sword','Weapon',2.50,10,'MainHand',NULL,0,1,NULL,NULL),(2,'Long Sword','Weapon',3.20,14,'MainHand',NULL,0,1,NULL,NULL),(3,'Battle Axe','Weapon',4.10,18,'MainHand',NULL,0,1,NULL,NULL),(4,'War Hammer','Weapon',4.80,20,'MainHand',NULL,0,1,NULL,NULL),(5,'Dagger','Weapon',1.10,6,'OffHand',NULL,0,1,NULL,NULL),(6,'Leather Helmet','Armor',1.50,NULL,NULL,'Helmet',0,1,NULL,2),(7,'Iron Helmet','Armor',2.20,NULL,NULL,'Helmet',0,1,NULL,5),(8,'Steel Helmet','Armor',2.60,NULL,NULL,'Helmet',0,1,NULL,7),(9,'Leather Chestplate','Armor',4.00,NULL,NULL,'Chestpiece',0,1,NULL,4),(10,'Iron Chestplate','Armor',5.80,NULL,NULL,'Chestpiece',0,1,NULL,8),(11,'Steel Chestplate','Armor',6.50,NULL,NULL,'Chestpiece',0,1,NULL,10),(12,'Leather Boots','Armor',1.00,NULL,NULL,'Boots',0,1,NULL,2),(13,'Iron Greaves','Armor',2.80,NULL,NULL,'Boots',0,1,NULL,5),(14,'Steel Greaves','Armor',3.30,NULL,NULL,'Boots',0,1,NULL,7),(15,'Wooden Shield','Armor',3.00,NULL,NULL,'OffHand',0,1,NULL,6),(16,'Iron Shield','Armor',4.00,NULL,NULL,'OffHand',0,1,NULL,9),(17,'Health Potion','Consumable',0.30,NULL,NULL,NULL,1,5,'Drinkable',NULL),(18,'Iron Arrow','Consumable',0.20,NULL,NULL,NULL,1,5,'Ammo',NULL),(19,'Big rock','Consumable',0.50,NULL,NULL,NULL,1,5,'Throwable',NULL),(20,'Leather leggings','Armor',1.20,NULL,NULL,'Legs',0,1,NULL,4),(21,'Iron leggings','Armor',3.10,NULL,NULL,'Legs',0,1,NULL,10),(22,'Steel leggings','Armor',3.70,NULL,NULL,'Legs',0,1,NULL,14),(23,'Filled leggings of disturbing origins','Armor',1.20,NULL,NULL,'Legs',0,1,NULL,-2),(29,'Katana','Weapon',3.00,16,'MainHand',NULL,0,1,NULL,NULL),(30,'Rapier','Weapon',1.80,12,'MainHand',NULL,0,1,NULL,NULL),(31,'Battle Staff','Weapon',4.20,14,'TwoHanded',NULL,0,1,NULL,NULL),(32,'Halberd','Weapon',5.50,20,'TwoHanded',NULL,0,1,NULL,NULL),(33,'Throwing Axe','Weapon',2.20,9,'OffHand',NULL,0,1,NULL,NULL),(34,'Short Bow','Weapon',2.00,8,'TwoHanded',NULL,0,1,NULL,NULL),(35,'Long Bow','Weapon',2.50,12,'TwoHanded',NULL,0,1,NULL,NULL),(36,'Crossbow','Weapon',3.50,14,'TwoHanded',NULL,0,1,NULL,NULL),(37,'Spear','Weapon',3.80,13,'TwoHanded',NULL,0,1,NULL,NULL),(38,'Morning Star','Weapon',4.00,15,'MainHand',NULL,0,1,NULL,NULL),(39,'Twin Daggers','Weapon',1.20,10,'OffHand',NULL,0,1,NULL,NULL),(40,'War Pick','Weapon',4.50,18,'MainHand',NULL,0,1,NULL,NULL),(41,'Claymore','Weapon',5.00,22,'TwoHanded',NULL,0,1,NULL,NULL),(42,'Hand Crossbow','Weapon',1.50,7,'OffHand',NULL,0,1,NULL,NULL),(43,'Throwing Knife','Weapon',0.50,5,'OffHand',NULL,0,1,NULL,NULL),(44,'Flail','Weapon',3.70,16,'MainHand',NULL,0,1,NULL,NULL),(45,'Battle Hammer','Weapon',5.20,21,'TwoHanded',NULL,0,1,NULL,NULL),(46,'Spiked pauldrons','Armor',3.00,NULL,NULL,'Shoulders',0,1,NULL,5);
/*!40000 ALTER TABLE `worlditems` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-15 14:33:26