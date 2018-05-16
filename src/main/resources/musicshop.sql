-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: musicshop
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `albums`
--

DROP TABLE IF EXISTS `albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `albums` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `genre` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `albums`
--

LOCK TABLES `albums` WRITE;
/*!40000 ALTER TABLE `albums` DISABLE KEYS */;
INSERT INTO `albums` VALUES (1,'Лучшее из лучших','6','1','2018','active'),(2,'Золотые песни','5','1','2017','active'),(3,'Have a Nice Day','2','11','2005','active'),(4,'Кино, вино и домино','17','11','2013','active'),(5,'Guess Who','27','4','1971','active'),(6,'Master of Puppets','3','13','1986','active');
/*!40000 ALTER TABLE `albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `albums_content`
--

DROP TABLE IF EXISTS `albums_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `albums_content` (
  `album_id` varchar(10) NOT NULL,
  `track_id` varchar(10) NOT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`album_id`,`track_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `albums_content`
--

LOCK TABLES `albums_content` WRITE;
/*!40000 ALTER TABLE `albums_content` DISABLE KEYS */;
INSERT INTO `albums_content` VALUES ('1','11','active'),('1','12','active'),('1','127','active'),('1','129','active'),('1','131','active'),('1','132','active'),('1','133','active'),('1','139','active'),('1','142','active'),('1','144','active'),('1','3','active'),('1','4','deleted'),('2','113','active'),('2','120','active'),('2','123','active'),('2','124','active'),('2','125','active'),('2','14','active'),('2','15','active'),('2','16','active'),('2','18','active'),('2','19','active'),('2','21','active'),('2','24','active'),('2','26','active'),('2','31','active'),('3','102','active'),('3','104','active'),('3','108','active'),('3','71','active'),('3','78','active'),('3','80','active'),('3','88','active'),('4','389','active'),('4','390','active'),('4','392','active'),('4','393','active'),('4','395','active'),('4','396','active'),('5','427','active'),('5','428','active'),('5','429','active'),('5','430','active'),('5','431','active'),('6','271','active'),('6','272','active'),('6','273','active'),('6','277','active'),('6','280','active'),('6','282','active'),('6','287','active');
/*!40000 ALTER TABLE `albums_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `genre` varchar(5) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,'Michael Jackson','1','Певец','active'),(2,'Bon Jovi','11','Певец','active'),(3,'Metallica','13','Группа','active'),(5,'Алла Пугачева','1','Певец','active'),(6,'Филипп Киркоров','1','Певец','active'),(7,'Madonna','1','Певец','active'),(8,'Nirvana','11','Группа','active'),(9,'Aerosmith','11','Группа','active'),(10,'Paul McCartney','11','Певец','active'),(11,'Ozzy Osbourne','11','Певец','active'),(12,'Иванушки International','1','Группа','active'),(13,'Дискотека Авария','1','Группа','active'),(14,'ABBA','1','Группа','active'),(15,'Boney M','1','Группа','active'),(16,'Ария','11','Группа','active'),(17,'Чайф','11','Группа','active'),(18,'Земфира','11','Певец','active'),(19,'Найк Борзов','11','Певец','active'),(20,'Eric Clapton','4','Певец','active'),(21,'Лига Блюза','4','Группа','active'),(23,'Ma Rainey','4','Певец','active'),(24,'Bessie Smith','4','Певец','active'),(25,'Jimmy Rushing','4','Певец','active'),(26,'Robert Johnson','4','Певец','active'),(27,'B.B. King','4','Певец','active'),(28,'Bruce Dickinson','13','Певец','active'),(29,'Judas Priest','13','Группа','active'),(30,'Black Sabbath','13','Группа','active'),(31,'Легион','13','Группа','active'),(32,'Коррозия металла','13','Группа','active'),(40,'Frank Sinatra','12','Певец','active'),(41,'Glenn Miller','12','Певец','active');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genres` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (1,'Поп','active'),(3,'Нет','active'),(4,'Блюз','active'),(5,'Саксофон','deleted'),(11,'Рок','active'),(12,'Джаз','active'),(13,'Метал','active');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mixes`
--

DROP TABLE IF EXISTS `mixes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `genre` varchar(10) DEFAULT NULL,
  `year` varchar(5) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mixes`
--

LOCK TABLES `mixes` WRITE;
/*!40000 ALTER TABLE `mixes` DISABLE KEYS */;
INSERT INTO `mixes` VALUES (2,'Rock non-stop','11','2017','deleted'),(3,'Новинки лета','1','2007','active'),(4,'Rock collection','11','2013','active'),(5,'Легенды Русского Рока ','11','2017','active'),(6,'Blues Collection','4','2018','active'),(7,'Best Metal','13','2009','active'),(8,'Best of Cool Jazz','12','2001','active');
/*!40000 ALTER TABLE `mixes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mixes_content`
--

DROP TABLE IF EXISTS `mixes_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixes_content` (
  `mix_id` varchar(10) NOT NULL,
  `track_id` varchar(10) NOT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`mix_id`,`track_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mixes_content`
--

LOCK TABLES `mixes_content` WRITE;
/*!40000 ALTER TABLE `mixes_content` DISABLE KEYS */;
INSERT INTO `mixes_content` VALUES ('2','1','deleted'),('2','12','deleted'),('2','132','deleted'),('2','151','deleted'),('2','2','deleted'),('2','307','deleted'),('2','318','deleted'),('2','6','deleted'),('3','122','active'),('3','133','active'),('3','144','active'),('3','186','deleted'),('3','19','active'),('3','225','deleted'),('3','24','active'),('3','348','active'),('3','349','active'),('3','370','active'),('4','194','active'),('4','222','active'),('4','292','active'),('4','294','active'),('4','314','active'),('4','316','active'),('4','411','deleted'),('4','437','deleted'),('4','65','active'),('4','67','active'),('5','333','active'),('5','341','active'),('5','346','active'),('5','360','active'),('5','362','active'),('5','364','active'),('5','382','active'),('5','388','active'),('5','392','active'),('5','403','active'),('6','241','active'),('6','398','active'),('6','410','active'),('6','411','active'),('6','415','active'),('6','419','active'),('6','421','active'),('6','424','active'),('6','431','active'),('7','280','active'),('7','419','active'),('7','439','active'),('7','451','active'),('7','460','active'),('7','477','active'),('7','484','active');
/*!40000 ALTER TABLE `mixes_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchased_albums`
--

DROP TABLE IF EXISTS `purchased_albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchased_albums` (
  `user_id` varchar(10) NOT NULL,
  `album_id` varchar(10) NOT NULL,
  PRIMARY KEY (`user_id`,`album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchased_albums`
--

LOCK TABLES `purchased_albums` WRITE;
/*!40000 ALTER TABLE `purchased_albums` DISABLE KEYS */;
INSERT INTO `purchased_albums` VALUES ('2','2'),('2','3'),('5','6');
/*!40000 ALTER TABLE `purchased_albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchased_mixes`
--

DROP TABLE IF EXISTS `purchased_mixes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchased_mixes` (
  `user_id` varchar(10) NOT NULL,
  `mix_id` varchar(10) NOT NULL,
  PRIMARY KEY (`user_id`,`mix_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchased_mixes`
--

LOCK TABLES `purchased_mixes` WRITE;
/*!40000 ALTER TABLE `purchased_mixes` DISABLE KEYS */;
INSERT INTO `purchased_mixes` VALUES ('2','2');
/*!40000 ALTER TABLE `purchased_mixes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchased_tracks`
--

DROP TABLE IF EXISTS `purchased_tracks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchased_tracks` (
  `user_id` varchar(10) NOT NULL,
  `track_id` varchar(10) NOT NULL,
  PRIMARY KEY (`user_id`,`track_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchased_tracks`
--

LOCK TABLES `purchased_tracks` WRITE;
/*!40000 ALTER TABLE `purchased_tracks` DISABLE KEYS */;
INSERT INTO `purchased_tracks` VALUES ('1','1'),('113','2'),('120','2'),('123','2'),('124','2'),('125','2'),('14','2'),('15','2'),('16','2'),('18','2'),('19','2'),('2','1'),('2','102'),('2','104'),('2','108'),('2','113'),('2','120'),('2','123'),('2','124'),('2','125'),('2','14'),('2','145'),('2','146'),('2','147'),('2','148'),('2','15'),('2','16'),('2','176'),('2','18'),('2','181'),('2','19'),('2','2'),('2','21'),('2','24'),('2','26'),('2','271'),('2','272'),('2','273'),('2','277'),('2','280'),('2','282'),('2','287'),('2','307'),('2','31'),('2','318'),('2','427'),('2','428'),('2','429'),('2','430'),('2','431'),('2','71'),('2','78'),('2','80'),('2','88'),('21','2'),('24','2'),('26','2'),('31','2'),('5','271'),('5','272'),('5','273'),('5','277'),('5','280'),('5','282'),('5','287');
/*!40000 ALTER TABLE `purchased_tracks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tracks`
--

DROP TABLE IF EXISTS `tracks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tracks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `genre` varchar(45) DEFAULT NULL,
  `year` varchar(5) DEFAULT NULL,
  `length` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=492 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tracks`
--

LOCK TABLES `tracks` WRITE;
/*!40000 ALTER TABLE `tracks` DISABLE KEYS */;
INSERT INTO `tracks` VALUES (1,'Beat it','1','1','1980','5.31','active'),(2,'It\'s my life','2','11','1985','4.23','active'),(3,'Я за тебя умру','6','1','2002','3.27','active'),(4,'Всё отдам я за любовь','6','1','2003','4.02','active'),(5,'Баллада о ледяном доме','6','1','2004','4.15','active'),(6,'Любовь, похожая на сон','6','1','2007','5.17','active'),(7,'Немного жаль','6','1','2008','4.07','active'),(8,'Просто подари','6','1','2009','4.03','active'),(9,'Эти глаза напротив','6','1','2010','4.52','active'),(10,'Снег','6','1','2010','4.49','active'),(11,'Americano','6','1','2014','4.36','active'),(12,'Мне мама тихо говорила','6','1','2014','4.15','active'),(13,'Любовь или обман','6','1','2016','4.24','active'),(14,'Любовь одна виновата','5','1','1977','3.16','active'),(15,'Арлекино','5','1','1975','4.30','active'),(16,'Сто часов счастья','5','1','1977','5.34','active'),(17,'Вот так случилось, мама','5','1','1980','3.42','active'),(18,'Женщина, которая поёт','5','1','1978','5.00','active'),(19,'Эти летние дожди','5','1','1980','4.10','active'),(20,'Скажи мне что-нибудь','5','1','1980','3.50','active'),(21,'Маэстро','5','1','1981','5.40','active'),(22,'Дежурный ангел','5','1','1981','4.33','active'),(23,'Я больше не ревную','5','1','1982','5.15','active'),(24,'Миллион роз','5','1','1982','5.30','active'),(25,'Цыганский хор','5','1','1983','4.36','active'),(26,'Любовью за любовь','5','1','1984','4.05','active'),(27,'Расскажите, птицы','5','1','1985','6.20','active'),(28,'Паромщик','5','1','1986','3.37','active'),(29,'Две звезды','5','1','1986','3.57','active'),(30,'Окраина','5','1','1987','4.50','active'),(31,'Примадонна','5','1','1997','3.03','active'),(32,'Белый снег','5','1','2000','3.57','active'),(33,'Мадам Брошкина','5','1','2000','4.48','active'),(34,'Got to Be There','1','1','1971','3.23','active'),(35,'Rockin\' Robin','1','1','1971','2.51','active'),(36,'Ben','1','1','1972','2.42','active'),(37,'Don’t Stop \'til You Get Enough','1','1','1979','3.59','active'),(38,'Rock With You','1','1','1979','3.20','active'),(39,'Off the Wall','1','1','1980','3.47','active'),(40,'She’s Out Of My Life','1','1','1980','3.36','active'),(41,'The Girl Is Mine','1','1','1982','3.41','active'),(42,'Billie Jean','1','1','1983','4.50','active'),(43,'Wanna Be Startin\' Somethin\'','1','1','1982','6.30','active'),(44,'Human Nature','1','1','1983','4.06','active'),(45,'P.Y.T.','1','1','1983','3.58','active'),(46,'Say Say Say','1','1','1983','3.55','active'),(47,'Thriller','1','1','1984','5.58','active'),(48,'We are the World','1','1','1985','7.14','active'),(49,'I Just Can\'t Stop Loving You','1','1','1985','4.25','active'),(50,'Bad','1','1','1987','4.07','active'),(51,'The Way You Make Me Feel','1','1','1987','4.26','active'),(52,'Man In The Mirror','1','1','1988','4.55','active'),(53,'Dirty Diana','1','1','1988','4.22','active'),(54,'Smooth Criminal','1','1','1989','4.17','active'),(55,'Black Or White','1','1','1991','4.15','active'),(56,'Remember The Time','1','1','1991','4.00','active'),(57,'In The Closet ','1','1','1992','6.31','active'),(58,'Will You Be There','1','1','1993','7.39','active'),(59,'Scream ','1','1','1995','4.37','active'),(60,'You are Not Alone','1','1','1995','5.45','active'),(61,'You Rock My World','1','1','2001','5.38','active'),(62,'Love Never Felt So Good','1','1','2014','4.04','active'),(63,'Runaway','2','11','1984','4.18','active'),(64,'Burning for Love','2','11','1984','6.37','active'),(65,'She Don’t Know Me','2','11','1984','4.02','active'),(66,'In and Out of Love','2','11','1985','4.25','active'),(67,'Only Lonely','2','11','1985','5.02','active'),(68,'Silent Night','2','11','1985','5.07','active'),(69,'Tokyo Road','2','11','1985','5.41','active'),(70,'The Hardest Part Is the Night','2','11','1985','4.25','active'),(71,'Always Run to You','2','11','1985','5.00','active'),(72,'To the Fire','2','11','1985','4.27','active'),(73,'Let It Rock','2','11','1986','5.25','active'),(74,'You Give Love a Bad Name','2','11','1986','3.44','active'),(75,'Livin\' on a Prayer','2','11','1986','4.09','active'),(76,'Social Disease','2','11','1986','4.18','active'),(77,'Wanted Dead or Alive',NULL,'11','1986','5.08','active'),(78,'Never Say Goodbye','2','11','1986','4.48','active'),(79,'Lay Your Hands on Me','2','11','1988','6.00','active'),(80,'Bad Medicine','2','11','1988','5.19','active'),(81,'Born to Be My Baby','2','11','1988','4.40','active'),(82,'Living in Sin','2','11','1988','4.39','active'),(83,'I’ll Be There for You','2','11','1988','5.46','active'),(84,'I Believe','2','11','1992','5.48','active'),(85,'Keep the Faith','2','11','1992','5.46','active'),(86,'I\'ll Sleep When I\'m Dead','2','11','1992','4.43','active'),(87,'In These Arms','2','11','1992','5.19','active'),(88,'Bed of Roses','2','11','1992','6.34','active'),(89,'Dry County','2','11','1992','9.52','active'),(90,'Hey God','2','11','1995','6.10','active'),(91,'Something for the Pain','2','11','1995','4.46','active'),(92,'This Ain’t a Love Song','2','11','1995','5.06','active'),(93,'These Days','2','11','1995','6.26','active'),(94,'Lie to Me','2','11','1995','5.34','active'),(95,'It’s My Life','2','11','2000','3.44','active'),(96,'Say It Isn’t So','2','11','2000','3.33','active'),(97,'Thank You for Loving Me','2','11','2000','5.09','active'),(98,'One Wild Night','2','11','2000','4.18','active'),(99,'Have a Nice Day','2','11','2005','3.49','active'),(100,'Welcome to Wherever You Are','2','11','2005','3.47','active'),(101,'Who Says You Can\'t Go Home','2','11','2005','4.40','active'),(102,'This House Is Not for Sale','2','11','2016','3.36','active'),(103,'Living With the Ghost','2','11','2016','4.44','active'),(104,'Knockout','2','11','2016','3.29','active'),(105,'Labor of Love','2','11','2016','5.03','active'),(106,'Born Again Tomorrow','2','11','2016','3.33','active'),(107,'Roller Coaster','2','11','2016','3.40','active'),(108,'New Year\'s Day','2','11','2016','4.27','active'),(109,'Не отрекаются любя','5','1','1977','4.08','active'),(110,'Мне нравится','5','1','1979','1.34','active'),(111,'Музыкант','5','1','1979','2.53','active'),(112,'Этот мир','5','1','1980','1.43','active'),(113,'Как тревожен этот путь','5','1','1982','','active'),(114,'Ах, как хочется жить','5','1','1985','1985','active'),(115,'Every night and every day','5','1','1985','4.00','active'),(116,'All the time you were right here','5','1','1985','4.50','active'),(117,'Сто друзей','5','1','1986','3.35','active'),(118,'Когда я уйду','5','1','1987','4.35','active'),(119,'Фотограф','5','1','1990','4.00','active'),(120,'Между небом и землей','5','1','2001','3/15','active'),(121,'Мэри','5','1','1995','4/33','active'),(122,'Мал-помалу ','5','1','1998','3/07','active'),(123,'Речной трамвайчик','5','1','2001','3/22','active'),(124,'Будь или не будь','5','1','2003','3/39','active'),(125,'Любовь как состояние','5','1','2008','3.19','active'),(126,'Странные люди','6','1','2002','4.37','active'),(127,'Влюблённый и безумно одинокий','6','1','2002','3.34','active'),(128,'Мечта','6','1','2003','3.55','active'),(129,'Танец Алабама','6','1','2003','2.32','active'),(130,'Свадебные цветы','6','1','2004','4.30','active'),(131,'Мир без твоей любви','6','1','2004','4.03','active'),(132,'Попурри','6','1','2007','4.40','active'),(133,'Только раз','6','1','2007','4.23','active'),(134,'Я не Рафаэль','6','1','2009','5.04','active'),(135,'Галки','6','1','2008','3.53','active'),(136,'Шалом','6','1','2009','4.29','active'),(137,'Дискобой','6','1','2010','3.13','active'),(138,'Кристина','6','1','2011','4.47','active'),(139,'L’amore','6','1','2011','4.27','active'),(140,'Любовь пять звёзд','6','1','2014','3.54','active'),(141,'Сады вишнёвые','6','1','2014','4.22','active'),(142,'Забываю','6','1','2015','3.38','active'),(143,'Гимн несбывшимся мечтам','6','1','2015','4.43','active'),(144,'Сиртаки','6','1','2016','4.15','active'),(145,'Ring Ring ','14','1','1973','3.03','active'),(146,'Another Town, Another Train','14','1','1973','3.10','active'),(147,'Disillusion','14','1','1973','3.05','active'),(148,'People Need Love','14','1','1973','2.43','active'),(149,'I Saw it in the Mirror','14','1','1973','2.33','active'),(150,'Nina, Pretty Ballerina','14','1','1973','2.52','active'),(151,'Waterloo','14','1','1974','2.44','active'),(152,'Sitting in the Palmtree','14','1','1974','3.37','active'),(153,'King Kong Song','14','1','1974','3.11','active'),(154,'Hasta Mañana','14','1','1974','3.09','active'),(155,'My Mama Said','14','1','1974','3.13','active'),(156,'Dance','14','1','1974','3.12','active'),(157,'Mamma Mia','14','1','1975','3.32','active'),(158,'Hey, Hey Helen','14','1','1975','3.17','active'),(159,'Tropical Loveland','14','1','1975','3.06','active'),(160,'SOS','14','1','1975','3.23','active'),(161,'Man in the Middle','14','1','1975','3.03','active'),(162,'Bang-A-Boomerang','14','1','1975','2.50','active'),(163,'I Do, I Do, I Do, I Do, I Do','14','1','1975','3.17','active'),(164,'Rock Me','14','1','1975','3.06','active'),(165,'Intermezzo No. 1','14','1','1975','3.48','active'),(166,'I’ve Been Waiting for You','14','1','1975','3.41','active'),(167,'So Long','14','1','1975','3.06','active'),(168,'When I Kissed the Teacher','14','1','1976','3.03','active'),(169,'Dancing Queen','14','1','1976','3.51','active'),(170,'My Love, My Life','14','1','1976','3.52','active'),(171,'Dum Dum Diddle','14','1','1976','2.55','active'),(172,'Knowing Me, Knowing You','14','1','1976','4.00','active'),(173,'Eagle','14','1','1977','5.53','active'),(174,'Take a Chance on Me','14','1','1977','4.03','active'),(175,'One Man, One Woman','14','1','1977','4.37','active'),(176,'The Name of the Game','14','1','1977','4.53','active'),(177,'As Good as New','14','1','1979','3.24','active'),(178,'Voulez-Vous','14','1','1979','5.09','active'),(179,'I Have a Dream','14','1','1979','4.49','active'),(180,'Angeleyes','14','1','1979','4.20','active'),(181,'The King Has Lost His Crown','14','1','1979','3.32','active'),(182,'The Visitors','14','1','1981','5.47','active'),(183,'Head over Heels','14','1','1980','3.48','active'),(184,'When All Is Said and Done','14','1','1980','3.17','active'),(185,'Soldiers','14','1','1980','4.41','active'),(186,'I Let the Music Speak','14','1','1980','5.23','active'),(187,'One of Us','14','1','1980','3.57','active'),(188,'Two for the Price of One','14','1','1980','3.38','active'),(189,'Slipping Through My Fingers','14','1','1980','3.53','active'),(190,'Like An Angel Passing Through My Room','14','1','1980','3.40','active'),(191,'Make It','9','11','1973','3.38','active'),(192,'Somebody','9','11','1973','3.45','active'),(193,'Dream On','9','11','1973','4.27','active'),(194,'One Way Street','9','11','1973','7.00','active'),(195,'Mama Kin','9','11','1973','4.27','active'),(196,'Write Me a Letter','9','11','1973','4.10','active'),(197,'Movin’ Out','9','11','1973','5.02','active'),(198,'Walkin’ the Dog','9','11','1973','5.02','active'),(199,'Same Old Song and Dance','9','11','1974','3.53','active'),(200,'Lord of the Thighs','9','11','1974','4.14','active'),(201,'Spaced','9','11','1974','4.21','active'),(202,'Woman of the World','9','11','1974','5.49','active'),(203,'S.O.S','9','11','1974','2.51','active'),(204,'Train Kept A-Rollin\'','9','11','1974','5.33','active'),(205,'Seasons of Wither','9','11','1974','5.38','active'),(206,'Pandora’s Box','9','11','1974','5.43','active'),(207,'Toys in the Attic','9','11','1975','3.05','active'),(208,'Uncle Salty','9','11','1975','4.10','active'),(209,'Adam’s Apple','9','11','1975','4.34','active'),(210,'Walk This Way','9','11','1975','4.40','active'),(211,'Big Ten Inch Record','9','11','1975','2.16','active'),(212,'Sweet Emotion','9','11','1975','4.34','active'),(213,'No More No More','9','11','1976','4.34','active'),(214,'Round and Round','9','11','1976','5.03','active'),(215,'You See Me Crying','9','11','1976','5.12','active'),(216,'Back in the Saddle','9','11','1976','4.39','active'),(217,'Last Child','9','11','1976','3.27','active'),(218,'Rats in the Cellar','9','11','1976','4.06','active'),(219,'Combination','9','11','1976','3.37','active'),(220,'Sick as a Dog','9','11','1976','4.12','active'),(221,'Nobody’s Fault','9','11','1976','4.25','active'),(222,'Get the Lead Out','9','11','1976','3.42','active'),(223,'Lick and a Promise','9','11','1976','3.05','active'),(224,'Home Tonight','9','11','1976','3.16','active'),(225,'Nightflight to Venus','15','1','1978','5.15','active'),(226,'Rasputin','15','1','1978','5.10','active'),(227,'Painter Man','15','1','1978','3.45','active'),(228,'He Was A Steppenwolf','15','1','1978','6.13','active'),(229,'King Of The Road','15','1','1978','3.26','active'),(230,'Slunky','20','4','1970','3.34','active'),(231,'Bad Boy','20','4','1970','3.34','active'),(232,'Lonesome and a Long Way from Home','20','4','1970','3.29','active'),(233,'After Midnight','20','4','1970','2.51','active'),(234,'Easy Now','20','4','1970','2.57','active'),(235,'Blues Power','20','4','1970','3.09','active'),(236,'Alabama Woman Blue','20','4','1916','5.06','active'),(237,'Can\'t Let You Do It','20','4','1916','3.50','active'),(238,'I Will Be There','20','4','2016','4.37','active'),(239,'Spiral','20','4','2016','5.04','active'),(240,'Catch the Blues','20','4','2016','4.51','active'),(241,'Cypress Grove','20','4','2016','4.49','active'),(242,'Little Man, You\'ve Had a Busy Day','20','4','2016','3.11','active'),(243,'Stones in My Passway','20','4','','4.03','active'),(244,'I Dreamed I Saw St. Augustine','20','4','2016','4.02','active'),(245,'I\'ll Be Alright','20','4','2016','4.23','active'),(246,'Somebody\'s Knockin','20','4','2016','5.11','active'),(247,'I\'ll Be Seeing You','20','4','2016','5.00','active'),(248,'Lucky Star','7','1','1983','5.37','active'),(249,'Borderline','7','1','1983','5.18','active'),(250,'Burning Up','7','1','1983','3.44','active'),(251,'I Know It','7','1','1983','3.45','active'),(252,'Holiday','7','1','1983','6.08','active'),(253,'Think of Me','7','1','1983','4.53','active'),(254,'Physical Attraction','7','1','1983','6.36','active'),(255,'Everybody','7','1','1983','4.57','active'),(256,'Living for Love','7','1','2015','4.53','active'),(257,'Devil Pray','7','1','2015','4.05','active'),(258,'Ghosttown','7','1','2015','4.08','active'),(259,'Unapologetic Bitch','7','1','2015','3.50','active'),(260,'Illuminati','7','1','2015','3.43','active'),(261,'Bitch I\'m Madonna','7','1','2015','3.47','active'),(262,'Hold Tight','7','1','2015','3.37','active'),(263,'Joan of Arc','7','1','2015','4.01','active'),(264,'Iconic','7','1','2015','4.33','active'),(265,'HeartBreakCity','7','1','2015','3.33','active'),(266,'Body Shop','7','1','2015','3.39','active'),(267,'Holy Water','7','1','2015','4.09','active'),(268,'Inside Out','7','1','2015','4.23','active'),(269,'Wash All Over Me','7','1','2015','4.00','active'),(270,'Hit the Lights','3','13','1983','4.17','active'),(271,'The Four Horsemen','3','13','1983','7.13','active'),(272,'Motorbreath','3','13','1983','3.08','active'),(273,'Jump in the Fire','3','13','1983','4.42','active'),(274,'Whiplash','3','13','1983','4.10','active'),(275,'Phantom Lord','3','13','1983','5.02','active'),(276,'No Remorse','3','13','1983','6.26','active'),(277,'Seek & Destroy','3','13','1983','6.55','active'),(278,'Metal Militia','3','13','1983','5.10','active'),(279,'Hardwired','3','13','2016','3.09','active'),(280,'Atlas, Rise!','3','13','2016','6.26','active'),(281,'Now That We\'re Dead','3','13','2016','6.59','active'),(282,'Moth Into Flame','3','13','2016','5.50','active'),(283,'Dream No More','3','13','2016','6.29','active'),(284,'Halo on Fire','3','13','2016','8.15','active'),(285,'Confusion','3','13','2016','6.41','active'),(286,'ManUNkind','3','13','2016','6.55','active'),(287,'Here Comes Revenge','3','13','2016','7.17','active'),(288,'Am I Savage?','3','13','2016','6.29','active'),(289,'Murder One','3','13','2016','5.45','active'),(290,'Spit Out the Bone','3','13','2016','7.09','active'),(291,'Blew','8','11','1989','2.55','active'),(292,'Floyd the Barber','8','11','1989','2.18','active'),(293,'About a Girl','8','11','1989','2.48','active'),(294,'School','8','11','1989','2.42','active'),(295,'Love Buzz','8','11','1989','3.35','active'),(296,'WaterlooPaper Cuts','8','1','1974','2.44','active'),(297,'Negative Creep','8','1','1976','2.55','active'),(298,'Scoff','8','11','1989','4.10','active'),(299,'Swap Meet','8','11','1989','3.03','active'),(300,'Mr. Moustache','8','11','1989','3.24','active'),(301,'Sifting','8','11','1989','5.22','active'),(302,'Serve the Servants','8','11','1993','3.36','active'),(303,'Scentless Apprentice','8','11','1993','3.48','active'),(304,'Heart-Shaped Box','8','11','1993','4.41','active'),(305,'Rape Me','8','11','1993','2.50','active'),(306,'Dumb','8','11','1993','2.32','active'),(307,'Very Ape','8','11','1993','1.56','active'),(308,'Milk It','8','11','1993','3.55','active'),(309,'Pennyroyal Tea','8','11','1993','3.37','active'),(310,'tourette’s','8','11','1993','1.35','active'),(311,'All Apologies','8','11','1993','3.51','active'),(312,'War Pigs','11','11','1970','7.58','active'),(313,'Paranoid','11','11','1970','2.52','active'),(314,'Planet Caravan','11','11','1970','4.34','active'),(315,'Iron Man','11','11','1970','5.58','active'),(316,'Electric Funeral','11','11','1970','4.52','active'),(317,'Hand of Doom','11','11','1970','7.09','active'),(318,'Rat Salad','11','11','1970','2.30','active'),(319,'Fairies Wear Boots','11','11','1970','6.13','active'),(320,'The Lovely Linda','10','11','1970','0.42','active'),(321,'That Would Be Something','10','11','1970','2.37','active'),(322,'Valentine Day','10','11','1970','1.40','active'),(323,'Every Night','10','11','1970','2.30','active'),(324,'Hot as Sun/Glasses','10','11','1970','2.06','active'),(325,'Junk','10','11','1970','1.54','active'),(326,'Man We Was Lonely','10','11','1970','2.57','active'),(327,'Oo You','10','11','1970','2.47','active'),(328,'Momma Miss America','10','11','1970','4.04','active'),(329,'Teddy Boy','10','11','1970','2.22','active'),(330,'Singalong Junk','10','11','1970','2.34','active'),(331,'Maybe I\'m Amazed','10','11','1970','3.49','active'),(332,'Kreen-Akrore','10','11','1970','4.14','active'),(333,'Там высоко','16','11','2004','4.32','active'),(334,'Без тебя','16','11','2004','4.22','active'),(335,'Штиль','16','11','2004','5.33','active'),(336,'Мечты','16','11','2004','4.30','active'),(337,'Замкнутый круг','16','11','1974','3.09','active'),(338,'Ангельская пыль','16','11','2004','5.57','active'),(339,'Возьми моё сердце','16','11','2004','4.11','active'),(340,'Пытка тишиной','16','11','2004','5.28','active'),(341,'Вампир','16','11','2004','5.29','active'),(342,'Осколок льда','16','11','2004','5.25','active'),(343,'Раскачаем этот мир','16','11','2004','5.43','active'),(344,'Всё, что было','16','11','2004','5.06','active'),(345,'Беги за солнцем','16','11','2004','5.53','active'),(346,'Беспечный ангел','16','11','2004','4.16','active'),(347,'Пробил час','13','1','2002','5.19','active'),(348,'Небо тебя найдёт','13','1','2002','6.04','active'),(349,'Свобода','13','1','2002','5.13','active'),(350,'Герой асфальта','13','1','2002','5.15','active'),(351,'Потерянный рай','13','1','2002','5.35','active'),(352,'You’d Better Believe Me','13','1','2002','4.00','active'),(353,'Машина смерти','13','1','2002','3.54','active'),(354,'Дай руку мне','13','1','2002','5.05','active'),(355,'Почему','18','11','1999','4.45','active'),(356,'Синоптик','18','11','1999','3.45','active'),(357,'Ромашки','18','11','1999','3.27','active'),(358,'Маечки','18','1','1974','3.13','active'),(359,'СПИД','18','11','1999','3.35','active'),(360,'Румба','18','11','1999','3.09','active'),(361,'Скандал','18','11','1999','2.48','active'),(362,'Непошлое','18','11','1999','3.31','active'),(363,'Припевочка','18','11','1999','2.58','active'),(364,'–140','18','11','1999','3.53','active'),(365,'Ариведерчи','18','11','1999','2.48','active'),(366,'Ракеты','18','11','1999','2.49','active'),(367,'Земфира','18','11','1999','3.58','active'),(368,'Снегири','12','1','1999','4.20','active'),(369,'Два океана','12','1','1999','3.50','active'),(370,'Об этом я буду кричать всю ночь','12','1','1999','3.30','active'),(371,'Дуня-Дуняша','12','1','1999','4.08','active'),(372,'Тополиный пух','12','1','1999','3.51','active'),(373,'Небо','12','1','1999','3.53','active'),(374,'Туман','12','1','1999','3.37','active'),(375,'Рядом Ты','12','1','1999','4.07','active'),(376,'Зима','12','1','1999','3.29','active'),(377,'День рождения','12','1','1999','3.32','active'),(378,'Бардак в голове','19','11','2000','3.21','active'),(379,'Верхом на звезде','19','11','2000','3.38','active'),(380,'Супермен','19','11','2000','3.47','active'),(381,'Три слова','19','11','2000','4.36','active'),(382,'В будущем Луны','19','11','2000','4.35','active'),(383,'Художник','19','1','1974','3.12','active'),(384,'Загадка','19','11','2000','3.36','active'),(385,'Последняя песня','19','11','2000','3.00','active'),(386,'Виртуальная шарада','19','11','2000','3.42','active'),(387,'С войны','17','11','1990','5.31','active'),(388,'Матрос','17','11','1990','4.32','active'),(389,'Оставь нам нашу любовь','17','11','1990','4.45','active'),(390,'Открытие','17','11','1990','3.58','active'),(391,'Там, где нет ничего','17','11','1990','3.21','active'),(392,'Никто не услышит','17','11','1990','4.25','active'),(393,'По-другому не прожить','17','11','1990','5.03','active'),(394,'Давай вернёмся','17','11','1990','5.26','active'),(395,'Ты моя крепость','17','11','1990','4.48','active'),(396,'Поплачь о нём','17','11','1990','6.18','active'),(397,'Honest I Do','21','4','1991','3.01','active'),(398,' Июльский Блюз','21','4','1991','4.24','active'),(399,'Развяжи мне руки','21','4','1991','5.06','active'),(400,'Walking the dog','21','4','1991','3.22','active'),(401,'Не верь мне, Мой мышонок','21','4','1991','2.51','active'),(402,'Ночной Пловец','21','4','1991','5.01','active'),(403,'Ей нужен герой','19','4','1991','3.22','active'),(404,'Открой глаза, дружище','21','4','1991','4.05','active'),(405,'Рок-н-ролл Мяу-мяу','21','4','1991','3.19','active'),(406,'Июльский Блюз','21','4','1991','4.23','active'),(407,'See See Rider Blues','23','4','1910','3.19','active'),(408,' Moonshine Blues','23','4','1910','2.50','active'),(409,' Last Minute Blues','23','4','1910','2.55','active'),(410,'Those All Night Long Blues','23','4','1910','3.05','active'),(411,' Lost Wandering Blues','22','4','1910','2.47','active'),(412,' Ain\'t Nobody Business If I Do','24','4','1920','3.26','active'),(413,' St. Louis BluesRemastered','24','4','1920','3.08','active'),(414,' Down Hearted Blues','24','4','1920','3.28','active'),(415,' T\'ain\'t Nobody\'s Biz-Ness If I Do','24','4','1920','3.26','active'),(416,' Saint Louis Blues 2','24','4','1920','3.10','active'),(417,'Sings the Blues','25','4','1955','2.11','active'),(418,' Blue Skies','25','4','1964','2.36','active'),(419,' I Keep Remembering ','25','4','1968','2.47','active'),(420,' I\'m Gonna See My Baby ','25','4','1963','2.48','active'),(421,' You Can Depend On Me','25','4','1960','3.10','active'),(422,' Stop Breakin\' Down Blues','26','4','1933','2.16','active'),(423,' Crossroad Blues','26','4','1933','2.31','active'),(424,' Cross Road Blues','26','4','1933','2.29','active'),(425,'Me and the Devil Blues','26','4','1933','2.31','active'),(426,' Sweet Home Chicago','26','4','1933','2.57','active'),(427,' Singin\' the Blues','27','4','1957','3.15','active'),(428,'The Blues','27','4','1958','3.05','active'),(429,'My Kind of Blues','27','4','1960','2.44','active'),(430,'Live at the Regal ','27','4','1965','3.36','active'),(431,'Lucille','27','4','1968','3.10','active'),(432,'Head On','28','13','1980','3.12','active'),(433,'Shock Tactics ','28','13','1981','3.06','active'),(434,'The Number of the Beast','28','13','1982','3.12','active'),(435,'Piece of Mind ','28','13','1983','3.39','active'),(436,'Powerslave ','28','13','1984','4.09','active'),(437,'Somewhere in Time','28','13','1986','2.54','active'),(438,'Seventh Son of a Seventh Son','28','13','1988','3.15','active'),(439,'No Prayer for the Dying ','28','13','1990','4.31','active'),(440,'Fear of the Dark ','28','13','1992','4.12','active'),(441,'Brave New World','28','13','2000','5.06','active'),(442,'Dance of Death ','28','13','2003','4.56','active'),(443,'A Matter of Life and Death','28','13','2006','3.14','active'),(444,'The Final Frontier','28','1','1973','2.52','active'),(445,'The Book Of Souls','28','13','2015','4.06','active'),(446,'Firepower','29','13','2018','3.27','active'),(447,'Lightning Strike','29','13','2018','3.29','active'),(448,'Evil Never Dies','29','13','2018','4.23','active'),(449,'Never the Heroes','29','13','2018','4.23','active'),(450,'Necromancer','29','13','2018','3.33','active'),(451,'Children of the Sun','29','13','2018','4.00','active'),(452,'Guardians','29','13','2018','1.06','active'),(453,'Rising from Ruins','29','13','2018','5.23','active'),(454,'Flame Thrower','29','13','2018','4.34','active'),(455,'Spectre','29','13','2018','4.25','active'),(456,'Traitors Gate','29','13','2018','5.43','active'),(457,'No Surrender','29','13','2018','2.54','active'),(458,'Lone Wolf','29','13','2018','5.09','active'),(459,'Sea of Red','25','4','2018','5.51','active'),(460,'Black Sabbath','30','13','1970','6.16','active'),(461,'The Wizard','30','13','1970','4.24','active'),(462,'Behind the Wall of Sleep','30','13','1970','3.38','active'),(463,'N.I.B.','30','13','1970','6.06','active'),(464,'The Illusion of Power','30','13','1995','4.51','active'),(465,'Get a Grip','30','13','1995','3.58','active'),(466,'Can\'t Get Close Enough','30','13','1995','4.27','active'),(467,'Shaking Off the Chains','30','13','1995','4.02','active'),(468,'I Won\'t Cry for You','30','13','1995','4.47','active'),(469,'Guilty as Hell','30','13','1995','3.27','active'),(470,'Лунный свет','31','13','2010','3.47','active'),(471,'Избранный судьбой','31','13','2010','4.19','active'),(472,'Два крыла','31','13','2010','3.28','active'),(473,'Невидимый воин','31','1','1974','3.09','active'),(474,'Будь сильней','31','13','2010','3.41','active'),(475,'Афродита','31','13','2010','5.28','active'),(476,'Последний единорог','31','13','2010','4.27','active'),(477,'Победитель','31','13','2010','5.01','active'),(478,'Свет волшебства','31','13','2010','5.46','active'),(479,'Колокол','31','13','2010','5.26','active'),(480,'Звезда','31','13','2010','4.57','active'),(481,'Съешь живьём','32','13','1989','5.37','active'),(482,'Russian Vodka','32','13','1989','3.48','active'),(483,'Танк вампира','32','13','1989','6.45','active'),(484,'Пятнадцать человек на сундук с мертвецом ','32','13','1989','7.04','active'),(485,'Чёрный корабль','32','13','1989','3.48','active'),(486,'K.K.K.','32','13','1989','3.45','active'),(487,'Crazy House','32','13','1989','4.57','active'),(488,'Зов теней','32','13','1989','4.37','active'),(489,'Низвержение в Мальмстрим','32','13','1989','6.31','active'),(490,'Нойз','32','13','1989','6.14','active'),(491,'De Buen Humor','41','12','2004','3.18','active');
/*!40000 ALTER TABLE `tracks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `points` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Nikolay','Adminovich','root','root','nik@adminovich.by','admin','active',0),(2,'Freddy','Bush','qwe','asd','alfie@mail.ru','user','active',10),(3,'Kate','Kurlovich','konsuela','123','kate@tut.by','user','active',0),(5,'Дмитрий','Петрович','petya','qwe','petya@mail.ru','user','active',3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-16 17:42:06
