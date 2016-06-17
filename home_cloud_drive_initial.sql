CREATE DATABASE  IF NOT EXISTS `home_cloud_drive` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `home_cloud_drive`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: home_cloud_drive
-- ------------------------------------------------------
-- Server version	5.6.14-log

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
-- Table structure for table `family_trash`
--

DROP TABLE IF EXISTS `family_trash`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `family_trash` (
  `id_family_trash` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The primary key of the table',
  `original_relative_path` varchar(65) NOT NULL COMMENT 'The location of the deleted file relative to the drive''s top-level directory.',
  `share_relative_path` varchar(65) DEFAULT NULL COMMENT 'The location of the deleted file''s shared temp directory relative to the drive''s top-level directory.',
  `is_directory` varchar(1) NOT NULL COMMENT 'Indicates whether the deleted item is a directory (Y) or not (N)',
  `dt_last_update` timestamp NULL DEFAULT NULL COMMENT 'The last time the record was updated',
  PRIMARY KEY (`id_family_trash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table contains information about directories and files the user has requested to be removed.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family_trash`
--

LOCK TABLES `family_trash` WRITE;
/*!40000 ALTER TABLE `family_trash` DISABLE KEYS */;
/*!40000 ALTER TABLE `family_trash` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The id of the user',
  `email_address` varchar(45) NOT NULL COMMENT 'The email address of the user',
  `password` varbinary(50) NOT NULL COMMENT 'The encrypted password field of the user',
  `temp_password` varchar(20) DEFAULT NULL COMMENT 'The temporary password created for the user until their email address has been validated',
  `first_name` varchar(45) DEFAULT NULL COMMENT 'The first name of the user',
  `last_name` varchar(45) DEFAULT NULL COMMENT 'The last name of the user',
  `role` varchar(1) NOT NULL COMMENT 'This column stores the role of the user. The possible user roles are Business (B), Promoter (P) and Admin (A)',
  `is_subscribed` varchar(1) DEFAULT NULL COMMENT 'This column is an indicator that indicates whether the user is subscribed to our mailing list. All users are automatically subscribed to the mailing list and "unsubscribe" links are included in all promotional emails to allow users to unsubscribe',
  `dt_sign_up` datetime NOT NULL COMMENT 'The date that the user signed up',
  `dt_last_update` timestamp NULL DEFAULT NULL COMMENT 'The last time the record was updated',
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `uk_email_address` (`email_address`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='This table holds all pertinent information about a user that the application needs to perform required functions';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'testuser1@gmail.com','ÈGfWàêï{\"&¢4óÕ',NULL,NULL,NULL,'S','Y','2016-06-12 15:00:58','2016-06-12 19:00:58'),(2,'testadmin1@gmail.com','ÈGfWàêï{\"&¢4óÕ',NULL,NULL,NULL,'A','Y','2016-06-12 15:02:32','2016-06-12 19:02:32');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_trash`
--

DROP TABLE IF EXISTS `user_trash`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_trash` (
  `id_user_trash` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The primary key of the table',
  `id_user` int(11) NOT NULL COMMENT 'the id of the user that deleted the item',
  `original_relative_path` varchar(65) NOT NULL COMMENT 'The location of the deleted file relative to the drive''s top-level directory.',
  `share_relative_path` varchar(65) DEFAULT NULL COMMENT 'The location of the deleted file''s shared temp directory relative to the drive''s top-level directory.',
  `is_directory` varchar(1) NOT NULL COMMENT 'Indicates whether the deleted item is a directory (Y) or not (N)',
  `dt_last_update` timestamp NULL DEFAULT NULL COMMENT 'The last time the record was updated',
  PRIMARY KEY (`id_user_trash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table contains information about directories and files the user has requested to be removed.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_trash`
--

LOCK TABLES `user_trash` WRITE;
/*!40000 ALTER TABLE `user_trash` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_trash` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'home_cloud_drive'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-16 21:09:22
