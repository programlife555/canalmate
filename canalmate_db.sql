-- --------------------------------------------------------
-- 主机:                           10.114.24.175
-- 服务器版本:                        5.6.40 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 canal_db.canal_server_config 结构
CREATE TABLE IF NOT EXISTS `canal_server_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增列id',
  `canal_server_type` varchar(50) NOT NULL COMMENT 'server类型',
  `canal_home` varchar(500) NOT NULL COMMENT 'canal的软件地址',
  `canal_server_name` varchar(100) NOT NULL COMMENT 'canal server名字',
  `canal_server_host` varchar(50) NOT NULL COMMENT 'server地址',
  `canal_server_port` varchar(50) NOT NULL COMMENT 'server端口',
  `canal_server_configuration` text NOT NULL COMMENT '详细的配置文件',
  `standby_server_host` varchar(50) NOT NULL COMMENT 'standby server地址',
  `standby_server_port` varchar(50) NOT NULL COMMENT 'standby server端口',
  `standby_server_configuration` text NOT NULL COMMENT 'standby server配置',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `canal_server_name` (`canal_server_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- 导出  表 canal_db.canal_warn 结构
CREATE TABLE IF NOT EXISTS `canal_warn` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增列id',
  `warn_type` varchar(50) NOT NULL COMMENT '报警类型',
  `warn_message` varchar(500) NOT NULL COMMENT '报警消息',
  `warn_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报警日期',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  canal_db.canal_warn 的数据：~113,084 rows (大约)
DELETE FROM `canal_warn`;
/*!40000 ALTER TABLE `canal_warn` DISABLE KEYS */;
/*!40000 ALTER TABLE `canal_warn` ENABLE KEYS */;

-- 导出  表 canal_db.client_config 结构
CREATE TABLE IF NOT EXISTS `client_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增列id',
  `destination_id` bigint(20) NOT NULL COMMENT 'destination对应的ID',
  `path` varchar(500) NOT NULL COMMENT 'client jar 地址',
  `host` varchar(50) NOT NULL COMMENT 'client地址',
  `client_name` varchar(50) NOT NULL COMMENT 'client名字',
  `zk_servers` varchar(500) NOT NULL COMMENT 'zookeeper名字',
  `filter` varchar(1000) NOT NULL COMMENT '过滤条件',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_name` (`client_name`,`destination_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='client列表';

-- 导出  表 canal_db.destinations_config 结构
CREATE TABLE IF NOT EXISTS `destinations_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增列id',
  `canal_id` bigint(20) NOT NULL COMMENT 'canal server对应的ID',
  `destination_name` varchar(50) NOT NULL COMMENT 'destination名字',
  `description` varchar(200) NOT NULL COMMENT 'destination描述',
  `destination_configuration` text NOT NULL COMMENT 'description详细的配置文件',
  `standby_configuration` text NOT NULL COMMENT 'standby description详细的配置文件',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `destination_name` (`destination_name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='destination列表';



-- 导出  表 canal_db.tb_config 结构
CREATE TABLE IF NOT EXISTS `tb_config` (
  `pk_config_id` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色表主键',
  `config_name` varchar(30) NOT NULL COMMENT '属性名',
  `config_value` varchar(16) NOT NULL COMMENT '属性值',
  `create_user` varchar(30) DEFAULT NULL COMMENT '登记人',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新人',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`pk_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统的属性配置表';

-- 正在导出表  canal_db.tb_config 的数据：~0 rows (大约)
DELETE FROM `tb_config`;
/*!40000 ALTER TABLE `tb_config` DISABLE KEYS */;
INSERT INTO `tb_config` (`pk_config_id`, `config_name`, `config_value`, `create_user`, `update_user`, `inserttime`, `updatetime`, `isactive`) VALUES
	(1, 'is_send_mail', '0', NULL, NULL, '2018-06-13 15:34:52', '2018-06-22 11:15:45', 1);
/*!40000 ALTER TABLE `tb_config` ENABLE KEYS */;

-- 导出  表 canal_db.tb_menu 结构
CREATE TABLE IF NOT EXISTS `tb_menu` (
  `pk_menu_id` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单表主键',
  `menu_code` varchar(30) NOT NULL COMMENT '菜单编号',
  `menu_name` varchar(20) NOT NULL COMMENT '菜单名称',
  `superior_menu` varchar(30) DEFAULT NULL COMMENT '父菜单',
  `menu_url` varchar(30) DEFAULT NULL COMMENT '菜单地址',
  `menu_icon` varchar(80) DEFAULT NULL COMMENT '菜单图标',
  `menu_serial` int(2) DEFAULT NULL COMMENT '菜单序号',
  `create_user` varchar(30) DEFAULT NULL COMMENT '登记人',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新人',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`pk_menu_id`),
  UNIQUE KEY `uniq_menu_code` (`menu_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 正在导出表  canal_db.tb_menu 的数据：~13 rows (大约)
DELETE FROM `tb_menu`;
/*!40000 ALTER TABLE `tb_menu` DISABLE KEYS */;
INSERT INTO `tb_menu` (`pk_menu_id`, `menu_code`, `menu_name`, `superior_menu`, `menu_url`, `menu_icon`, `menu_serial`, `create_user`, `update_user`, `inserttime`, `updatetime`, `isactive`) VALUES
	(1, '1000', '进程管理', '0', '/process', 'fa fa-id-card', 0, 'system', 'system', '2018-05-10 10:25:15', '2018-06-01 09:57:23', 1),
	(2, '1001', '延迟监控', '0', '/delay', 'fa fa-hourglass-start', 1, 'system', 'system', '2018-05-10 10:35:18', '2018-05-31 13:43:43', 1),
	(3, '1002', '配置管理', '0', '/server', 'fa fa-sitemap', 2, 'system', 'system', '2018-05-10 10:35:18', '2018-05-31 13:43:53', 1),
	(5, '1004', 'pipeline', '1003', '/group/pipeline1', NULL, 1, 'system', 'system', '2018-05-10 10:35:18', '2018-05-16 14:07:05', 0),
	(6, '1005', 'instance配置', '1004', '/group/instance', NULL, 0, 'system', 'system', '2018-05-10 10:35:18', '2018-05-16 14:07:08', 0),
	(7, '1006', 'client配置', '1004', '/group/client', NULL, 1, 'system', 'system', '2018-05-10 10:35:18', '2018-05-16 14:07:12', 0),
	(8, '1007', 'server配置管理default', '1002', '/default', NULL, 0, 'system', 'system', '2018-05-10 10:35:18', '2018-05-14 16:20:04', 1),
	(9, '1008', 'pipeline', '1007', '/default/pipeline1', NULL, 1, 'system', 'system', '2018-05-10 10:35:18', '2018-05-16 14:07:17', 0),
	(10, '1009', 'instance配置', '1008', '/default/instance', NULL, 0, 'system', 'system', '2018-05-10 10:35:18', '2018-05-16 14:07:20', 0),
	(11, '1010', 'client配置', '1008', '/default/client', NULL, 1, 'system', 'system', '2018-05-10 10:35:18', '2018-05-16 14:07:22', 0),
	(12, '1011', '系统管理', '0', '/system', 'fa fa-address-book', 3, 'system', 'system', '2018-05-30 10:38:40', '2018-05-31 13:44:01', 1),
	(13, '1012', '用户管理', '1011', '/system/user', NULL, 0, 'system', 'system', '2018-05-30 10:39:33', '2018-05-30 10:39:58', 1);
/*!40000 ALTER TABLE `tb_menu` ENABLE KEYS */;

-- 导出  表 canal_db.tb_menu_role 结构
CREATE TABLE IF NOT EXISTS `tb_menu_role` (
  `pk_menu_role_code` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单角色编号',
  `menu_code` varchar(30) NOT NULL COMMENT '菜单编号',
  `menu_name` varchar(20) NOT NULL COMMENT '菜单名称',
  `role_code` varchar(30) DEFAULT NULL,
  `role_name` varchar(16) NOT NULL COMMENT '权限名称',
  `create_user` varchar(30) DEFAULT NULL COMMENT '登记人',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新人',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`pk_menu_role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='菜单角色表';

-- 正在导出表  canal_db.tb_menu_role 的数据：~15 rows (大约)
DELETE FROM `tb_menu_role`;
/*!40000 ALTER TABLE `tb_menu_role` DISABLE KEYS */;
INSERT INTO `tb_menu_role` (`pk_menu_role_code`, `menu_code`, `menu_name`, `role_code`, `role_name`, `create_user`, `update_user`, `inserttime`, `updatetime`, `isactive`) VALUES
	(1, '1000', '进程监控', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-10 11:06:39', 1),
	(2, '1001', '延迟监控', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-10 11:06:39', 1),
	(3, '1002', '配置管理', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-10 11:06:40', 1),
	(4, '1003', 'server配置管理group', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-10 11:06:41', 1),
	(5, '1004', 'pipeline', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-16 14:07:36', 0),
	(6, '1005', 'instance配置', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-16 14:07:40', 0),
	(7, '1006', 'client配置', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-16 14:07:45', 0),
	(8, '1007', 'server配置管理default', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-10 11:06:44', 1),
	(9, '1008', 'pipeline', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-16 14:07:49', 0),
	(10, '1009', 'instance配置', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-16 14:07:53', 0),
	(11, '1010', 'client配置', '1', '管理员', 'system', 'system', '2018-05-10 11:06:08', '2018-05-16 14:07:56', 0),
	(12, '1011', '系统管理', '1', '管理员', 'system', 'system', '2018-05-30 11:04:07', '2018-05-30 11:04:16', 1),
	(13, '1012', '用户管理', '1', '管理员', 'system', 'system', '2018-05-30 11:05:07', '2018-05-30 11:05:07', 1),
	(14, '1000', '进程监控', '2', '普通用户角色', 'system', 'system', '2018-06-21 16:37:30', '2018-06-21 16:38:33', 1),
	(15, '1001', '延迟监控', '2', '普通用户角色', 'system', 'system', '2018-06-21 16:38:17', '2018-06-21 16:38:34', 1);
/*!40000 ALTER TABLE `tb_menu_role` ENABLE KEYS */;

-- 导出  表 canal_db.tb_role 结构
CREATE TABLE IF NOT EXISTS `tb_role` (
  `pk_role_code` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色表主键',
  `role_code` varchar(30) NOT NULL COMMENT '角色编号',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `create_user` varchar(30) DEFAULT NULL COMMENT '登记人',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新人',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`pk_role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  canal_db.tb_role 的数据：~2 rows (大约)
DELETE FROM `tb_role`;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` (`pk_role_code`, `role_code`, `role_name`, `create_user`, `update_user`, `inserttime`, `updatetime`, `isactive`) VALUES
	(1, '1', '管理员', 'system', 'system', '2018-05-10 10:43:01', '2018-05-10 10:46:41', 1),
	(2, '2', '普通用户角色', 'system', 'system', '2018-05-10 10:43:01', '2018-06-06 11:18:07', 1);
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;

-- 导出  表 canal_db.tb_user 结构
CREATE TABLE IF NOT EXISTS `tb_user` (
  `pk_user_id` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户表主键',
  `user_code` varchar(30) NOT NULL COMMENT '用户编号',
  `user_name` varchar(16) NOT NULL COMMENT '用户名称',
  `user_password` varchar(50) NOT NULL COMMENT '用户密码',
  `user_mobile` varchar(15) DEFAULT NULL COMMENT '手机',
  `user_email` varchar(30) DEFAULT NULL COMMENT 'E-mail',
  `superior` varchar(30) DEFAULT NULL COMMENT '上级编号',
  `create_user` varchar(30) DEFAULT NULL COMMENT '登记人',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新人',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`pk_user_id`),
  UNIQUE KEY `uniq_user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  canal_db.tb_user 的数据：~4 rows (大约)
DELETE FROM `tb_user`;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` (`pk_user_id`, `user_code`, `user_name`, `user_password`, `user_mobile`, `user_email`, `superior`, `create_user`, `update_user`, `inserttime`, `updatetime`, `isactive`) VALUES
	(1, 'admin', '管理员', '62F3E352309274A34146AB271B3CE5E2', '123456789', 'admin@xxx.com', NULL, NULL, NULL, '2018-05-10 10:03:51', '2018-05-30 16:39:39', 1),
	/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;

-- 导出  表 canal_db.tb_user_role 结构
CREATE TABLE IF NOT EXISTS `tb_user_role` (
  `pk_user_role_code` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色表主键',
  `user_code` varchar(30) NOT NULL COMMENT '用户编号',
  `user_name` varchar(16) NOT NULL COMMENT '用户名称',
  `role_code` varchar(30) NOT NULL COMMENT '角色编号',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `create_user` varchar(30) DEFAULT NULL COMMENT '登记人',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新人',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记日期',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isactive` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`pk_user_role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 正在导出表  canal_db.tb_user_role 的数据：~5 rows (大约)
DELETE FROM `tb_user_role`;
/*!40000 ALTER TABLE `tb_user_role` DISABLE KEYS */;
INSERT INTO `tb_user_role` (`pk_user_role_code`, `user_code`, `user_name`, `role_code`, `role_name`, `create_user`, `update_user`, `inserttime`, `updatetime`, `isactive`) VALUES
	(1, 'admin', '管理员', '1', '管理员', NULL, NULL, '2018-06-21 16:43:24', '2018-06-21 16:43:24', 1);
/*!40000 ALTER TABLE `tb_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
