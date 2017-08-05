/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.28 : Database - xbq
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`xbq` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `xbq`;

/*Table structure for table `building` */

DROP TABLE IF EXISTS `building`;

CREATE TABLE `building` (
  `building_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `building_name` varchar(20) NOT NULL COMMENT '楼宇名称',
  `building_remark` varchar(150) NOT NULL COMMENT '备注',
  PRIMARY KEY (`building_id`),
  UNIQUE KEY `UNIQUE_NAME` (`building_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `building` */

insert  into `building`(`building_id`,`building_name`,`building_remark`) values (4,'10栋','南院男生寝室楼'),(5,'16栋','南院女生寝室楼'),(6,'11栋','南院寝室楼'),(7,'18栋','南院男生寝室楼');

/*Table structure for table `dorm` */

DROP TABLE IF EXISTS `dorm`;

CREATE TABLE `dorm` (
  `dorm_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `dorm_name` varchar(20) NOT NULL COMMENT '宿舍名称',
  `dorm_people_num` int(10) NOT NULL COMMENT '宿舍现居住人数',
  `dorm_tel` varchar(30) NOT NULL COMMENT '宿舍联系电话',
  `dorm_type` varchar(10) NOT NULL COMMENT '宿舍类型',
  `dorm_building` varchar(10) DEFAULT NULL COMMENT '宿舍所属楼宇',
  PRIMARY KEY (`dorm_id`),
  UNIQUE KEY `UNIQUE_DORM_NAME` (`dorm_name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `dorm` */

insert  into `dorm`(`dorm_id`,`dorm_name`,`dorm_people_num`,`dorm_tel`,`dorm_type`,`dorm_building`) values (18,'533',2,'7856464','四人间','10栋'),(19,'534',4,'5442888','四人间','10栋'),(20,'523',1,'7555555','四人间','10栋'),(21,'536',2,'5442666','四人间','10栋'),(22,'420',1,'9087767','四人间','16栋'),(23,'421',0,'5442000','四人间','16栋'),(24,'101',1,'7856464','四人间','18栋'),(25,'102',0,'5442888','四人间','18栋');

/*Table structure for table `dorm_building` */

DROP TABLE IF EXISTS `dorm_building`;

CREATE TABLE `dorm_building` (
  `building_id` bigint(20) DEFAULT NULL COMMENT '楼宇id',
  `dorm_id` bigint(20) DEFAULT NULL COMMENT '宿舍id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dorm_building` */

insert  into `dorm_building`(`building_id`,`dorm_id`) values (4,18),(5,22),(5,23),(4,21),(4,20),(4,19),(7,24),(7,25);

/*Table structure for table `loginlog` */

DROP TABLE IF EXISTS `loginlog`;

CREATE TABLE `loginlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `IPaddress` varchar(50) DEFAULT NULL COMMENT 'IP所属地',
  `loginDate` varchar(30) DEFAULT NULL COMMENT '登录时间',
  `loginIP` varchar(30) DEFAULT NULL COMMENT 'IP地址',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(20) DEFAULT NULL COMMENT '用户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=850 DEFAULT CHARSET=utf8;

/*Data for the table `loginlog` */

insert  into `loginlog`(`id`,`IPaddress`,`loginDate`,`loginIP`,`userId`,`userName`) values (27,'局域网','2016-04-01 23:05:45','10.104.221.204',1,'系统管理员'),(28,'局域网','2016-04-01 23:08:28','10.104.221.204',1,'系统管理员'),(29,'局域网','2016-04-02 10:43:08','10.104.215.21',1,'系统管理员'),(30,'局域网','2016-04-02 10:51:53','10.104.215.21',1,'徐邦启'),(32,'局域网','2016-04-02 10:55:40','10.104.215.21',1,'系统管理员'),(33,'局域网','2016-04-02 11:33:32','10.104.215.21',1,'系统管理员'),(34,'局域网','2016-04-02 12:08:12','10.104.215.21',1,'系统管理员'),(35,'局域网','2016-04-02 21:35:50','10.104.228.153',1,'系统管理员'),(36,'局域网','2016-04-03 10:17:26','10.104.223.199',1,'系统管理员'),(37,'局域网','2016-04-03 10:29:41','10.104.223.199',1,'系统管理员'),(835,'IANA','2016-05-20 23:36:23','127.0.0.1',1,'系统管理员'),(836,'IANA','2016-05-21 08:30:04','127.0.0.1',1,'系统管理员'),(837,'IANA','2016-05-21 09:24:45','127.0.0.1',1,'系统管理员'),(838,'IANA','2016-05-21 13:31:00','127.0.0.1',1,'系统管理员'),(839,'IANA','2016-05-21 15:26:32','127.0.0.1',1,'系统管理员'),(840,'IANA','2016-05-21 15:29:41','127.0.0.1',9,'刘德华'),(841,'IANA','2016-05-21 15:31:15','127.0.0.1',1,'系统管理员'),(842,'IANA','2016-05-21 15:31:52','127.0.0.1',45,'李四'),(843,'IANA','2016-05-21 15:32:28','127.0.0.1',46,'王五'),(844,'IANA','2016-05-21 15:33:59','127.0.0.1',45,'李四'),(845,'IANA','2016-05-21 15:36:00','127.0.0.1',9,'刘德华'),(846,'IANA','2016-05-21 15:37:34','127.0.0.1',45,'李四'),(847,'IANA','2016-05-21 15:38:26','127.0.0.1',9,'刘德华'),(848,'IANA','2017-08-05 11:17:09','127.0.0.1',1,'系统管理员'),(849,'','2017-08-05 11:26:11','0:0:0:0:0:0:0:1',1,'系统管理员');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `student_name` varchar(20) NOT NULL COMMENT '学生姓名',
  `student_class` varchar(20) NOT NULL COMMENT '所在班级',
  `student_phone` varchar(30) NOT NULL COMMENT '联系电话',
  `student_remark` varchar(150) NOT NULL COMMENT '备注',
  `student_sex` varchar(10) NOT NULL COMMENT '性别',
  `student_state` varchar(10) NOT NULL COMMENT '状态',
  `student_userName` varchar(20) NOT NULL COMMENT '用户名（学号）',
  `student_userPass` varchar(30) NOT NULL DEFAULT '888888' COMMENT '用户密码（初始密码888888）',
  `out_date` varchar(100) DEFAULT NULL COMMENT '迁出日期',
  `student_institution` varchar(20) NOT NULL COMMENT '所属学院',
  `student_major` varchar(20) NOT NULL COMMENT '所学专业',
  `student_building` varchar(20) DEFAULT NULL COMMENT '所在楼宇',
  `student_dorm` varchar(10) DEFAULT NULL COMMENT '所住寝室',
  `student_lackFlag` int(1) DEFAULT '0' COMMENT '是否缺勤标识（0 未缺勤）',
  `student_lackBeginTime` varchar(30) DEFAULT NULL COMMENT '缺勤开始时间',
  `student_lackEndTime` varchar(30) DEFAULT NULL COMMENT '缺勤结束时间',
  `student_headFlag` varchar(10) DEFAULT NULL COMMENT '是否班级负责人',
  `student_ifOk` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `UNIQUE_userName` (`student_userName`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`student_id`,`student_name`,`student_class`,`student_phone`,`student_remark`,`student_sex`,`student_state`,`student_userName`,`student_userPass`,`out_date`,`student_institution`,`student_major`,`student_building`,`student_dorm`,`student_lackFlag`,`student_lackBeginTime`,`student_lackEndTime`,`student_headFlag`,`student_ifOk`) values (30,'朱明朋','1204','15773000001','阿蒙','男','入住','14121500998','888888',NULL,'数学学院','信息与计算科学','10栋','533',1,NULL,NULL,NULL,NULL),(31,'谭焱','1204','15773001173','鸡哥','男','入住','14121503938','888888',NULL,'数学学院','信息与计算科学','10栋','534',1,NULL,NULL,NULL,NULL),(32,'郑泽辉','1204','15773000002','官人','男','入住','14121504645','888888',NULL,'数学学院','信息与计算科学','10栋','534',0,NULL,NULL,'是',NULL),(33,'徐超阳','1204','18390013456','江哥','男','入住','14121503567','888888',NULL,'数学学院','信息与计算科学','10栋','534',0,NULL,NULL,NULL,NULL),(35,'袁绍','1104','15667345691','毕业了','男','迁出','14111525612','888888','2015-06-16','数学学院','信息与计算科学','','',0,NULL,NULL,NULL,NULL),(36,'欧阳先锋','1503','15777501178','牛人','男','入住','14151609354','888888',NULL,'数学学院','信息与计算科学','10栋','536',0,NULL,NULL,NULL,NULL),(37,'赵晓红','1204','18709877890','学霸','女','入住','14121504345','888888',NULL,'数学学院','信息与计算科学','16栋','420',0,NULL,NULL,NULL,NULL),(38,'周邵阳','1204','15773001123','克伐','男','未入住','14121508555','888888',NULL,'数学学院','信息与计算科学',NULL,NULL,0,NULL,NULL,NULL,NULL),(39,'万康','1204','15660984567','主席','男','入住','14121504051','888888',NULL,'数学学院','信息与计算科学','10栋','533',0,NULL,NULL,NULL,NULL),(40,'徐邦启','1204','18390006556','徐先生','男','入住','14121503931','888888',NULL,'数学学院','信息与计算科学','10栋','534',1,NULL,NULL,NULL,NULL),(41,'魏队','1103','15773009347','毕业生','男','迁出','14111908560','888888','2015-05-15','数学学院','信息与计算科学','','',0,NULL,NULL,NULL,NULL),(42,'申明','1204','15773009989','麻拐','男','入住','14121500999','888888',NULL,'数学学院','信息与计算科学','10栋','536',0,NULL,NULL,NULL,NULL),(43,'上官锦','1204','15773009870','毕业了','男','迁出','14111800867','888888','2015-05-19','数学学院','信息与计算科学','','',0,NULL,NULL,NULL,NULL),(44,'张三','1204','18390006721','老三','男','入住','14121505587','888888',NULL,'数学学院','信息与计算科学','10栋','523',0,NULL,NULL,NULL,NULL),(45,'李四','1204','18390006554','毕业迁出','男','迁出','14121503123','888888','2016-05-21','计算机学院','软件工程','','',1,NULL,NULL,NULL,NULL),(46,'王五','1204','18390006554','5467547','男','入住','14121503456','888888',NULL,'计算机学院','软件工程','18栋','101',0,NULL,NULL,'是',NULL);

/*Table structure for table `student_dorm` */

DROP TABLE IF EXISTS `student_dorm`;

CREATE TABLE `student_dorm` (
  `dorm_id` bigint(20) DEFAULT NULL COMMENT '宿舍id',
  `student_id` bigint(20) DEFAULT NULL COMMENT '学生id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student_dorm` */

insert  into `student_dorm`(`dorm_id`,`student_id`) values (19,31),(19,32),(19,33),(18,30),(21,36),(22,37),(21,42),(18,39),(20,44),(19,40),(24,46);

/*Table structure for table `student_lack` */

DROP TABLE IF EXISTS `student_lack`;

CREATE TABLE `student_lack` (
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `student_name` varchar(20) NOT NULL COMMENT '学生姓名',
  `student_sex` varchar(5) NOT NULL COMMENT '学生性别',
  `student_institution` varchar(20) NOT NULL COMMENT '所属学院',
  `student_major` varchar(20) NOT NULL COMMENT '所学专业',
  `student_class` varchar(20) NOT NULL COMMENT '所在班级',
  `student_building` varchar(20) NOT NULL COMMENT '所在楼宇',
  `student_dorm` varchar(10) NOT NULL COMMENT '所住寝室',
  `student_lackBeginTime` varchar(30) NOT NULL COMMENT '缺勤开始时间',
  `student_lackEndTime` varchar(30) NOT NULL COMMENT '缺勤结束时间',
  `student_remark` varchar(150) NOT NULL COMMENT '缺勤原因',
  `student_ifOk` varchar(5) DEFAULT '否' COMMENT '是否确认',
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `student_lack` */

insert  into `student_lack`(`student_id`,`student_name`,`student_sex`,`student_institution`,`student_major`,`student_class`,`student_building`,`student_dorm`,`student_lackBeginTime`,`student_lackEndTime`,`student_remark`,`student_ifOk`) values (2,'徐邦启','男','数学学院','信息与计算科学','1204','10栋','534','2016-04-01 23:36:19','2016-04-02 12:36:30','外出上网！','是'),(3,'徐邦启','男','数学学院','信息与计算科学','1204','10栋','534','2016-04-04 17:40:08','2016-04-04 21:40:12','外出吃饭！','是'),(4,'李四','男','计算机学院','软件工程','1204','18栋','101','2016-05-21 15:33:03','2016-05-21 15:33:07','00000','是');

/*Table structure for table `system` */

DROP TABLE IF EXISTS `system`;

CREATE TABLE `system` (
  `system_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `system_userName` varchar(20) NOT NULL COMMENT '管理员名称',
  `system_userPass` varchar(30) NOT NULL COMMENT '管理员密码',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `system` */

insert  into `system`(`system_id`,`system_userName`,`system_userPass`) values (1,'admin','admin'),(2,'hnistadmin','hnist123!@#');

/*Table structure for table `tab_notice` */

DROP TABLE IF EXISTS `tab_notice`;

CREATE TABLE `tab_notice` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(20) NOT NULL COMMENT '标题',
  `content` varchar(100) NOT NULL COMMENT '内容',
  `send_userName` varchar(30) DEFAULT NULL COMMENT '发送人的学号/工号',
  `send_person` varchar(20) DEFAULT NULL COMMENT '发送人姓名',
  `send_role` varchar(10) DEFAULT NULL COMMENT '发送人角色',
  `send_time` varchar(20) DEFAULT NULL COMMENT '发送时间',
  `rec_userName` varchar(30) DEFAULT NULL COMMENT '接收人的学号/工号',
  `rec_person` varchar(20) DEFAULT NULL COMMENT '接收人姓名',
  `rec_role` varchar(10) DEFAULT NULL COMMENT '接收人角色',
  `see_state` varchar(10) DEFAULT '否' COMMENT '是否查看(1查看,2确认,回复)',
  `reply_state` varchar(10) DEFAULT '否' COMMENT '是否回复',
  `ok_state` varchar(10) DEFAULT '否' COMMENT '是否确认',
  `msg_type` varchar(20) DEFAULT NULL COMMENT '消息类型',
  `guid` varchar(100) DEFAULT NULL COMMENT 'GUID,确定唯一',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tab_notice` */

insert  into `tab_notice`(`id`,`title`,`content`,`send_userName`,`send_person`,`send_role`,`send_time`,`rec_userName`,`rec_person`,`rec_role`,`see_state`,`reply_state`,`ok_state`,`msg_type`,`guid`) values (1,'调换宿舍','调换宿舍到534        				','14121503931','徐邦启','student','2016-05-14 15:32:40','10_manage',NULL,'teacher','已确认','否','否','宿舍调换','81ebb77c-267d-4b2c-ae8e-abd68caab6d9'),(2,'调换宿舍','	       0000 				','14121503123','李四','student','2016-05-21 15:35:26','18_manage',NULL,'teacher','已确认','否','否','宿舍调换','c2b1e161-5d68-4699-837f-d2ae2fe27fa2'),(3,'毕业了','迁出宿舍	        				','14121503123','李四','student','2016-05-21 15:38:05','18_manage',NULL,'teacher','已确认','否','否','迁出申请','d948cba5-299c-4c8d-9899-6e075ddf08f5');

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `teacher_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `teacher_name` varchar(20) NOT NULL COMMENT '楼宇管理员名称',
  `teacher_sex` varchar(10) NOT NULL COMMENT '楼宇管理员性别',
  `teacher_tel` varchar(30) NOT NULL COMMENT '楼宇管理员电话',
  `teacher_userName` varchar(20) NOT NULL COMMENT '楼宇管理员名称',
  `teacher_userPass` varchar(30) NOT NULL DEFAULT '888888' COMMENT '楼宇管理员密码（初始密码888888）',
  PRIMARY KEY (`teacher_id`),
  UNIQUE KEY `UNIQUE_NAME` (`teacher_userName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `teacher` */

insert  into `teacher`(`teacher_id`,`teacher_name`,`teacher_sex`,`teacher_tel`,`teacher_userName`,`teacher_userPass`) values (5,'周杰伦','男','18900008888','10_manage','888888'),(6,'那英','女','15700098765','16_manage','888888'),(8,'梁朝伟','男','18709876789','11_manage','888888'),(9,'刘德华','男','18709876790','18_manage','888888');

/*Table structure for table `teacher_buidling` */

DROP TABLE IF EXISTS `teacher_buidling`;

CREATE TABLE `teacher_buidling` (
  `building_id` bigint(20) DEFAULT NULL COMMENT '楼宇id',
  `teacher_id` bigint(20) DEFAULT NULL COMMENT '楼宇管理员id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `teacher_buidling` */

insert  into `teacher_buidling`(`building_id`,`teacher_id`) values (4,5),(5,6),(6,8),(6,8),(8,9),(7,9);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
