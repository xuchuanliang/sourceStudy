CREATE DATABASE IF NOT EXISTS mybatis_source DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use mybatis_source;
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for uc_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `mybatis_source`.`blog` (`id`, `name`) VALUES ('1', '徐传良');
INSERT INTO `mybatis_source`.`blog` (`id`, `name`) VALUES ('2', '张鹤翔');
