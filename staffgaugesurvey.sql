/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : staffgaugesurvey

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2020-03-29 16:56:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cargo`
-- ----------------------------
DROP TABLE IF EXISTS `cargo`;
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

-- ----------------------------
-- Records of cargo
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `head_img_url` varchar(255) NOT NULL,
  `salt` varchar(30) NOT NULL,
  `phone_number` varchar(11) DEFAULT '',
  `permission` varchar(1) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '傅杰青2', 'D6C40A5D8C8CA743ACD5E636F728A141', 'http://images.nowcoder.com/head/800t.png', '50d01', '123456', 'B');
INSERT INTO `tb_user` VALUES ('2', '傅杰青', '95DCF6189083E601B308FC87D4A73E88', 'http://images.nowcoder.com/head/885t.png', 'fbd8a', '123456', 'A');
INSERT INTO `tb_user` VALUES ('3', 'fu', 'B5CF9EEF266CF4FD35536C4150923FF9', 'http://images.nowcoder.com/head/667t.png', '9cbaa', '123456', 'B');
INSERT INTO `tb_user` VALUES ('4', 'fujie', '8BE5805D9443276A5E86A8CBEF2386DF', 'http://images.nowcoder.com/head/67t.png', '4490a', '123456', 'B');
