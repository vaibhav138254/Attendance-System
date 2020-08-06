-- MySQL dump 10.13  Distrib 5.5.38, for Win64 (x86)
--
-- Host: localhost    Database: attendancedb
-- ------------------------------------------------------
-- Server version	5.5.38

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
-- Table structure for table `studentdetail`
--

DROP TABLE IF EXISTS `studentdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentdetail` (
  `STUDENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Date_Of_Birth` date DEFAULT NULL,
  `Address` varchar(300) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Mobile` varchar(20) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `COURSE` varchar(15) DEFAULT NULL,
  `BRANCH` varchar(15) DEFAULT NULL,
  `S_YEAR` varchar(10) DEFAULT NULL,
  `Semester` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`STUDENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentdetail`
--

LOCK TABLES `studentdetail` WRITE;
/*!40000 ALTER TABLE `studentdetail` DISABLE KEYS */;
INSERT INTO `studentdetail` VALUES (1,'Vaibhav','2009-05-12','New Delhi','22334455','9834567744','vaibhav12@gmail.com','Male','B.Tech','CSE','I','1st');
/*!40000 ALTER TABLE `studentdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useractivitymaster`
--

DROP TABLE IF EXISTS `useractivitymaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useractivitymaster` (
  `Activity_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_ID` int(11) DEFAULT NULL,
  `Login_Time` datetime DEFAULT NULL,
  `Logout_Time` datetime DEFAULT NULL,
  PRIMARY KEY (`Activity_ID`),
  KEY `fk_UserActivityMaster_UserMaster` (`User_ID`),
  CONSTRAINT `fk_UserActivityMaster_UserMaster` FOREIGN KEY (`User_ID`) REFERENCES `usermaster` (`User_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useractivitymaster`
--

LOCK TABLES `useractivitymaster` WRITE;
/*!40000 ALTER TABLE `useractivitymaster` DISABLE KEYS */;
INSERT INTO `useractivitymaster` VALUES (97,1,'2017-11-06 08:40:30','2017-11-06 08:41:33'),(98,1,'2017-11-06 10:22:48','2017-11-06 10:23:20'),(99,1,'2017-11-06 10:31:22','2017-11-06 10:31:32');
/*!40000 ALTER TABLE `useractivitymaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usermaster`
--

DROP TABLE IF EXISTS `usermaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usermaster` (
  `User_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `User_Type` varchar(20) DEFAULT NULL,
  `User_Status` varchar(20) DEFAULT NULL,
  `Security_Question` varchar(100) DEFAULT NULL,
  `Security_Answer` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usermaster`
--

LOCK TABLES `usermaster` WRITE;
/*!40000 ALTER TABLE `usermaster` DISABLE KEYS */;
INSERT INTO `usermaster` VALUES (1,'admin','admin','Administrator','Active','Which is your favourite color?','green'),(2,'employee','employee','Employee','Active','Which is your favourite color?','Sky Blue');
/*!40000 ALTER TABLE `usermaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userpersonaldetail`
--

DROP TABLE IF EXISTS `userpersonaldetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userpersonaldetail` (
  `User_ID` int(11) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Date_Of_Birth` date DEFAULT NULL,
  `Address` varchar(300) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Mobile` varchar(20) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`User_ID`),
  KEY `fk_UserPersonalDetail_UserMaster` (`User_ID`),
  CONSTRAINT `fk_UserPersonalDetail_UserMaster` FOREIGN KEY (`User_ID`) REFERENCES `usermaster` (`User_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userpersonaldetail`
--

LOCK TABLES `userpersonaldetail` WRITE;
/*!40000 ALTER TABLE `userpersonaldetail` DISABLE KEYS */;
INSERT INTO `userpersonaldetail` VALUES (1,'Randeep Singh','1985-05-07','# 1245\nSector 16, \nludhiana, punjab, india','017261243332','7888964442','rajeshkch.ad@gmail.com');
/*!40000 ALTER TABLE `userpersonaldetail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-06 10:32:19
