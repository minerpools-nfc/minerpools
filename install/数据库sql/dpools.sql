/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.95
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 192.168.100.95:3306
 Source Schema         : dpools

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 13/04/2021 09:28:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for zs_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `zs_admin_log`;
CREATE TABLE `zs_admin_log`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `operate_id` int(11) NOT NULL DEFAULT 0 COMMENT '操作管理员',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '操作url',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'ip',
  `user_agent` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'user-agent',
  `create_time` int(11) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `update_time` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `operateid`(`operate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理日志记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `zs_admin_user`;
CREATE TABLE `zs_admin_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_group_id` tinyint(1) NOT NULL DEFAULT 0 COMMENT '角色组id',
  `create_time` int(11) NOT NULL,
  `update_time` int(11) NOT NULL DEFAULT 0 COMMENT '更新时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `username` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `login_time` int(10) NOT NULL DEFAULT 0 COMMENT '登录时间',
  `login_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录IP',
  `nickname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `head_image` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zs_admin_user
-- ----------------------------
INSERT INTO `zs_admin_user` VALUES (1, 1, 1580695622, 1616708110, 1, 'admin', '14e1b600b1fd579f47433b88e8d85291', 1618193578, '192.168.10.112', 'admin', '/uploads/20181228/e5885dc546eb1369482cdf29fb07decd.jpg');

-- ----------------------------
-- Table structure for zs_auth_group
-- ----------------------------
DROP TABLE IF EXISTS `zs_auth_group`;
CREATE TABLE `zs_auth_group`  (
  `id` int(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色组',
  `create_time` int(11) NOT NULL,
  `update_time` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新时间',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态',
  `rules` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色组管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zs_auth_group
-- ----------------------------
INSERT INTO `zs_auth_group` VALUES (1, '超级管理员', 1580633995, 1616774711, 1, 'devices,bindusersIndex,releaseRecord,drawApply,alarmLog,earningsLog,systemConfig,emailConfig,interfaceConfig,alarmConfig,zsjobList,adminuserIndex,AuthGroupIndex');
INSERT INTO `zs_auth_group` VALUES (2, '测试', 1580634019, 1616550233, 1, '525,81,82,83,84,70');
INSERT INTO `zs_auth_group` VALUES (25, 'ce44', 1616590112, 0, 1, NULL);
INSERT INTO `zs_auth_group` VALUES (26, '23', 1616590156, 0, 1, NULL);
INSERT INTO `zs_auth_group` VALUES (27, 'ooo', 1616590315, 0, 1, NULL);
INSERT INTO `zs_auth_group` VALUES (28, 'yhh', 1616590861, 0, 1, NULL);
INSERT INTO `zs_auth_group` VALUES (29, 'yhhces', 1616635840, 0, 1, NULL);
INSERT INTO `zs_auth_group` VALUES (35, '45445444', 1616650803, 1616651811, 1, 'devices');
INSERT INTO `zs_auth_group` VALUES (37, 'ceshi', 1617259118, 1617272602, 1, 'bindusersIndex,devices,earningsLog,alarmLog');

-- ----------------------------
-- Table structure for zs_auth_rule
-- ----------------------------
DROP TABLE IF EXISTS `zs_auth_rule`;
CREATE TABLE `zs_auth_rule`  (
  `id` mediumint(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '控制器/方法',
  `title` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '菜单状态',
  `auth_set` tinyint(1) DEFAULT 1 COMMENT '角色权限可勾选设置1可以2不可以',
  `icon` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT 1,
  `sort` mediumint(8) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` int(11) DEFAULT 0 COMMENT '添加时间',
  `update_time` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新时间',
  `param` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '参数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 533 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zs_auth_rule
-- ----------------------------
INSERT INTO `zs_auth_rule` VALUES (528, 527, 'Devices/index', '设备列表', 1, 1, '', 1, 40, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (525, 0, 'profit', '分红管理', 1, 1, 'fa fa-money', 1, 50, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (527, 0, 'Device', '设备管理', 0, 0, 'fa fa-square', 1, 30, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (80, 0, 'DeviceAlarm', '预警记录', 1, 1, 'fa fa-asterisk', 1, 30, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (81, 0, 'Setting', '配置管理', 1, 1, '', 1, 30, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (82, 81, 'Setting/interface', '接口配置', 1, 1, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (83, 81, 'Setting/email', '邮件配置', 1, 1, '', 1, 2, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (84, 81, 'Setting/alarm', '预警配置', 1, 1, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (86, 80, 'ConfigType/index', '配置分组', 0, 0, 'fa fa-circle-o', 1, 30, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (87, 86, 'ConfigType/add', '新增', 0, 0, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (88, 86, 'ConfigType/edit', '修改', 0, 0, '', 1, 2, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (89, 86, 'ConfigType/delete', '删除', 0, 0, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (90, 86, 'ConfigType/switchChange', '列表开关', 0, 0, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (57, 1, 'AdminLog/index', '管理日志', 1, 0, '', 1, 10, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (58, 57, 'AdminLog/delete', '管理日志删除', 0, 0, '', 1, 0, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (60, 0, 'Access', '权限管理', 0, 0, 'fa fa-futbol-o', 1, 300, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (61, 0, 'User/index', '用户管理', 1, 1, '', 1, 30, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (62, 61, 'User/add', '新增', 0, 0, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (63, 61, 'User/edit', '修改', 0, 0, '', 1, 2, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (64, 61, 'User/delete', '删除', 0, 0, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (70, 0, 'AuthGroup/index', '角色管理', 1, 1, '', 1, 30, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (71, 70, 'AuthGroup/add', '新增', 0, 0, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (72, 70, 'AuthGroup/edit', '修改', 0, 0, '', 1, 2, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (73, 70, 'AuthGroup/delete', '删除', 0, 0, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (74, 70, 'AuthGroup/switchChange', '列表开关', 0, 0, '', 1, 1, 0, 0, '');
INSERT INTO `zs_auth_rule` VALUES (530, 528, 'Device/edit', '修改', 0, 0, '', 1, 2, 0, 0, '');

-- ----------------------------
-- Table structure for zs_bind_users
-- ----------------------------
DROP TABLE IF EXISTS `zs_bind_users`;
CREATE TABLE `zs_bind_users`  (
  `userid` bigint(60) NOT NULL,
  `loginname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录名',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `firstname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户姓',
  `lastname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码 (唯一)',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮件地址 (唯一)',
  `appid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '应用ID',
  `partnerid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '合作伙伴ID',
  `mbpoint` decimal(20, 8) DEFAULT NULL COMMENT '用户积分',
  `mbpoint_alarm` int(6) DEFAULT NULL COMMENT '积分是否充足  1  积分不足   0积分充足',
  `instime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `user_status` int(11) DEFAULT NULL COMMENT '用户状态 0-正常 1-未激活 2-锁定 3-作废 4-到期',
  `loginpwd` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '默认为空，界面设置',
  `deleteflag` int(6) DEFAULT NULL COMMENT '删除标志 1 正常2 系统移除 3人工移除',
  `wallet_address` varchar(122) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '钱包地址',
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_device_alarm
-- ----------------------------
DROP TABLE IF EXISTS `zs_device_alarm`;
CREATE TABLE `zs_device_alarm`  (
  `alarmid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键	uuid',
  `devicesn` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备SN',
  `userid` bigint(60) DEFAULT NULL COMMENT '用户ID',
  `alarm_type` int(6) DEFAULT NULL COMMENT '类型  	1 发生告警 0 恢复/删除告警',
  `firsttime` datetime(0) DEFAULT NULL COMMENT '首次发生时间',
  `lasttime` datetime(0) DEFAULT NULL COMMENT '最后发生时间',
  `servity` int(6) DEFAULT NULL COMMENT '告警等级 1 、恢复 5、通知 6、严重 7、紧急',
  `alarm_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '告警标题',
  `alarm_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '告警内容',
  `instime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `cfg_type` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '预计类型 interfacestatus 接口,onlinestatus 在线,mbpoint 积分',
  PRIMARY KEY (`alarmid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_device_alarm_config
-- ----------------------------
DROP TABLE IF EXISTS `zs_device_alarm_config`;
CREATE TABLE `zs_device_alarm_config`  (
  `cfgid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键	uuid',
  `mbpoint` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '积分预警阔值',
  `onlinestatus` tinyint(1) DEFAULT NULL COMMENT '离线状态预警	1 开启 2 关闭',
  `interfacestatus` tinyint(1) DEFAULT NULL COMMENT '接口状态预警1 开启 2 关闭',
  `email` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '预警接收邮箱,多个逗号分隔',
  `addtime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `addby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '新增数据用户	对于用户表',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `updateby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新用户',
  PRIMARY KEY (`cfgid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zs_device_alarm_config
-- ----------------------------
INSERT INTO `zs_device_alarm_config` VALUES ('1', '10', 1, 1, '', NULL, '0', '2021-04-02 14:47:40', '1');

-- ----------------------------
-- Table structure for zs_device_draw_detail
-- ----------------------------
DROP TABLE IF EXISTS `zs_device_draw_detail`;
CREATE TABLE `zs_device_draw_detail`  (
  `detailid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备id	uuid',
  `drawid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应分红设置表主键',
  `ctime` date DEFAULT NULL COMMENT '分红时间	yyyy-MM-dd',
  `devicesn` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备sn',
  `draw_rate` decimal(30, 4) DEFAULT NULL COMMENT '分红比例',
  `draw_num` decimal(30, 8) NOT NULL COMMENT '用户实际分红数量(币)',
  `draw_address` varchar(122) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分红发放地址',
  `draw_time` datetime(0) DEFAULT NULL COMMENT '分红发放时间',
  `draw_transFlag` int(6) DEFAULT NULL COMMENT '用户分红转账结果	1 待转账 2 转出交易中3转账成功 4转账失败5待提交执行',
  `draw_hash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '转账交易hash',
  `instime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`detailid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_device_draw_info
-- ----------------------------
DROP TABLE IF EXISTS `zs_device_draw_info`;
CREATE TABLE `zs_device_draw_info`  (
  `drawid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id	uuid',
  `draw_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `drawid_type` int(6) DEFAULT NULL COMMENT '类型	1 平均分配 2 按流量贡献比列',
  `startime` date DEFAULT NULL COMMENT '开始时间',
  `endtime` date DEFAULT NULL COMMENT '结束时间',
  `total_num` decimal(20, 8) NOT NULL COMMENT '分红总额 	单位 NFC',
  `device_num` int(6) DEFAULT NULL COMMENT '设备数量',
  `draw_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行状态  0、 待发放\r\n1、  已发放 \r\n2、发放失败 4部分成功部分失败，3保存待提交执行',
  `addtime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `addby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '新增数据用户	对于用户表',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `updateby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新用户',
  PRIMARY KEY (`drawid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_device_tocken_record
-- ----------------------------
DROP TABLE IF EXISTS `zs_device_tocken_record`;
CREATE TABLE `zs_device_tocken_record`  (
  `flowid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键	为ctime+devicesn的md5值',
  `ctime` date DEFAULT NULL COMMENT 'yyyy-MM-dd',
  `devicesn` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备sn',
  `flow_bill` decimal(30, 8) NOT NULL COMMENT '托管流量',
  `mbpoint` decimal(30, 8) NOT NULL COMMENT '消耗积分',
  `nfc_num` decimal(30, 8) NOT NULL COMMENT '获取NFC收益',
  `instime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `transfer_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '转账流水号,区块链转账时生成的，转账成功后回填',
  `trans_flag` int(11) DEFAULT NULL COMMENT '转账状态,1 成功  2失败   0 待转账  3划转中',
  `transtime` datetime(0) DEFAULT NULL COMMENT '转账时间',
  `share_rate` decimal(10, 4) DEFAULT NULL COMMENT '用户分成比例',
  `sharenum` decimal(30, 8) DEFAULT NULL COMMENT '用户分成(币)',
  `takenum` decimal(30, 8) DEFAULT NULL COMMENT '平台分成(币)每笔释放金额管理员抽成',
  `lockdays` int(6) DEFAULT NULL COMMENT '锁仓时长（天)',
  `releasedays` int(6) DEFAULT NULL COMMENT '释放时长（天）',
  `hasrelease` decimal(30, 8) DEFAULT NULL COMMENT '已释放数量',
  `release_status` int(6) DEFAULT NULL COMMENT '释放状态  1 未释放、2、释放完成（释放量==收益）3部分完成',
  `drawnum` decimal(30, 8) DEFAULT NULL COMMENT '已提取数量  ',
  `draw_status` int(6) DEFAULT NULL COMMENT '提取状态  1未提取、2、提取完成（提取量==释放量）、3、部分完成',
  `last_drawtime` datetime(0) DEFAULT NULL COMMENT '最后提取时间',
  PRIMARY KEY (`flowid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_devices
-- ----------------------------
DROP TABLE IF EXISTS `zs_devices`;
CREATE TABLE `zs_devices`  (
  `devicesn` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备sn',
  `devicestatus` int(6) DEFAULT NULL COMMENT '设备状态(0-未激活/1-正常/2-锁定/3-作废)',
  `online_status` int(6) DEFAULT NULL COMMENT '在线状态	1 在线  0离线',
  `delete_flag` int(6) DEFAULT NULL COMMENT '删除标志  1正常 2 删除',
  `device_class` int(6) DEFAULT NULL COMMENT '设备类型  1  物理token  2 云设备',
  `calval` decimal(20, 2) DEFAULT NULL COMMENT '算力	Mb/s',
  `calmodel` int(6) DEFAULT NULL COMMENT '份数	算力份数',
  `owerid` bigint(20) DEFAULT NULL COMMENT '设备owerid',
  `ower_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备拥有者',
  `buytime` datetime(0) DEFAULT NULL COMMENT '购买时间',
  `refereruserid` bigint(20) DEFAULT NULL COMMENT '推荐人ID',
  `referercode` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '推荐码',
  `referername` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '推荐人名字',
  `typeid` tinyint(1) DEFAULT NULL COMMENT '设备分组id',
  `createtime` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `online_time` datetime(0) DEFAULT NULL COMMENT '最后在线时间',
  `offline_time` datetime(0) DEFAULT NULL COMMENT '最后下线时间',
  `device_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备ip',
  `device_vip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '虚拟ip',
  `device_localtion` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备所在位置',
  `share_rate` decimal(10, 4) DEFAULT NULL COMMENT '分成比例',
  `lockdays` int(11) DEFAULT NULL COMMENT '锁仓时长（天）',
  `releasedays` int(11) DEFAULT NULL COMMENT '线性释放时长（天）',
  PRIMARY KEY (`devicesn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_devices_type
-- ----------------------------
DROP TABLE IF EXISTS `zs_devices_type`;
CREATE TABLE `zs_devices_type`  (
  `id` smallint(6) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分组名称',
  `create_time` int(11) NOT NULL DEFAULT 0,
  `update_time` int(11) NOT NULL DEFAULT 0,
  `sort` mediumint(8) DEFAULT 50 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_dpool_interface
-- ----------------------------
DROP TABLE IF EXISTS `zs_dpool_interface`;
CREATE TABLE `zs_dpool_interface`  (
  `interid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键	uuid',
  `inter_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口名称',
  `httpurl` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备获取url',
  `nfcurl` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收益获取url',
  `username` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'weline账号',
  `userpwd` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'weline密码',
  `intaval` int(6) DEFAULT NULL COMMENT '间隔时间  分钟',
  `addtime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `addby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '新增数据用户	对于用户表',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `updateby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新用户',
  PRIMARY KEY (`interid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zs_dpool_interface
-- ----------------------------
INSERT INTO `zs_dpool_interface` VALUES ('1', '接口配置', 'https://106.75.150.141:8443/v2/eapi/dbpool/getdata?partid=Y1DMATNYSMZPOKC3R8NJ&appid=CN6SDL3H5K4UL55YP77L', 'https://65.19.174.235:8443/v2/eapi/dbpool/getdata?partid=Y1DMATNYSMZPOKC3R8NJ&appid=CN6SDL3H5K4UL55YP77L', '', '', 5, '2021-03-31 10:21:10', '1', '2021-04-02 09:33:05', '1');

-- ----------------------------
-- Table structure for zs_draw_apply
-- ----------------------------
DROP TABLE IF EXISTS `zs_draw_apply`;
CREATE TABLE `zs_draw_apply`  (
  `applyid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键	uuid',
  `userid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请人',
  `wallet_address` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '钱包地址',
  `draw_num` decimal(30, 8) NOT NULL COMMENT '提成金额 管理员对应收益',
  `share_num` decimal(30, 8) NOT NULL COMMENT '提取金额  用户提取',
  `trans_status` int(6) DEFAULT NULL COMMENT '提取状态	1 提币申请中 2 提币完成 3后台转账中',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  `addtime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `addby` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `updateby` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `trans_no` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`applyid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_email_setting
-- ----------------------------
DROP TABLE IF EXISTS `zs_email_setting`;
CREATE TABLE `zs_email_setting`  (
  `emailid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键	uuid',
  `emailaddr` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱地址',
  `email_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '显示名称',
  `hostname` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发件服务器	Eg :smtp.exmail.qq.com',
  `authname` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱用户',
  `authpwd` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱密码',
  `ssl_enable` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ssl 发送模式',
  `eamilport` int(8) DEFAULT NULL COMMENT '端口',
  `addtime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `addby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '新增数据用户	对于用户表',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `updateby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新用户',
  PRIMARY KEY (`emailid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zs_email_setting
-- ----------------------------
INSERT INTO `zs_email_setting` VALUES ('1', '123@qq.com', '123', 'smtp.exmail.qq.com', '123', '111111', '1', 465, '2021-02-01 17:48:39', '1', '2021-04-02 14:47:29', '1');

-- ----------------------------
-- Table structure for zs_job
-- ----------------------------
DROP TABLE IF EXISTS `zs_job`;
CREATE TABLE `zs_job`  (
  `jobid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 uuid',
  `job_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务名称',
  `job_servers` int(11) DEFAULT NULL COMMENT '服务号',
  `act_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '线程路径',
  `job_enable` int(11) DEFAULT NULL COMMENT '是否启用,1 启用  0 停止',
  `job_cron` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行策略,crontab格式',
  `starttime` datetime(0) DEFAULT NULL COMMENT '任务开始时间',
  `endtime` datetime(0) DEFAULT NULL COMMENT '任务结束时间',
  `exectime` datetime(0) DEFAULT NULL COMMENT '最后执行时间',
  `execresult` int(11) DEFAULT NULL COMMENT '最后执行结果,1 成功 0 失败',
  `execmsg` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '执行结果',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `updateby` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`jobid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zs_job
-- ----------------------------
INSERT INTO `zs_job` VALUES (1, '锁仓释放任务', 100, 'com.dpool.app.release.ReleaseGenSrv', 1, '0 0/5 * * * ?', '2021-03-25 00:00:00', NULL, '2021-04-09 09:40:01', 1, '', '2021-04-09 11:00:19', '1');

-- ----------------------------
-- Table structure for zs_job_log
-- ----------------------------
DROP TABLE IF EXISTS `zs_job_log`;
CREATE TABLE `zs_job_log`  (
  `id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键 uuid',
  `jobid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '对应定时任务主键',
  `exectime` datetime(0) DEFAULT NULL COMMENT '执行时间',
  `speeds` bigint(20) DEFAULT NULL COMMENT '消耗时间 ms',
  `status` int(11) DEFAULT NULL COMMENT '执行结果 1 成功 0 失败 ',
  `jobmsg` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '执行内容',
  `job_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '执行详细内容',
  `instime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_release_record
-- ----------------------------
DROP TABLE IF EXISTS `zs_release_record`;
CREATE TABLE `zs_release_record`  (
  `releaseid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键	uuid',
  `flowid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键	uuid',
  `ctime` date DEFAULT NULL COMMENT 'yyyy-MM-dd',
  `devicesn` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备sn',
  `userid` bigint(60) DEFAULT NULL COMMENT '用户id',
  `nfc_num` decimal(30, 8) NOT NULL COMMENT '已释放金额',
  `share_rate` decimal(10, 4) DEFAULT NULL COMMENT '分成比例',
  `sharenum` decimal(30, 8) DEFAULT NULL COMMENT '分成金额(币)',
  `takenum` decimal(30, 8) DEFAULT NULL COMMENT '提成金额(币) 每笔释放金额管理员抽成',
  `applyid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提币申请ID',
  `draw_status` int(6) DEFAULT NULL COMMENT '提取状态	1 未提币  2 提币申请中  3、 提币完成 4、提币失败',
  `transtime` datetime(0) DEFAULT NULL COMMENT '转账时间',
  `instime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`releaseid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zs_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `zs_sys_config`;
CREATE TABLE `zs_sys_config`  (
  `cfgid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `prop_flag` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标志',
  `prop_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性名称',
  `prop_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标签',
  `prop_value` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性值',
  `prop_type` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '值类型 String/Int/Double/Datetime',
  `prop_units` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位 NFC %',
  `orderby` int(6) DEFAULT NULL COMMENT '排序ID',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `Updateby` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`cfgid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zs_sys_config
-- ----------------------------
INSERT INTO `zs_sys_config` VALUES ('1', 'lockdays', '锁仓时长', '设备收益在锁仓期间将冻结，直到到期后，才能释放提取', '1', 'int', '天', NULL, '2021-04-02 14:45:40', NULL);
INSERT INTO `zs_sys_config` VALUES ('2', 'releasedays', '线性释放时长', '设备收益锁仓到期后，按天根据总释放时长进行线性释放', '3', 'int', '天', NULL, '2021-04-02 14:45:40', NULL);
INSERT INTO `zs_sys_config` VALUES ('3', 'default_sharetype', '默认分成比例', '设备收益按照该比例进行用户和平台分成，如果设置为80，则其中80%为用户所有。（支持两位小数）', '90', 'double', '%', NULL, '2021-04-02 14:45:40', NULL);
INSERT INTO `zs_sys_config` VALUES ('4', 'min_drawnum', '用户最小提取数量', '用户提取申请的最小数量。', '1', 'double', '', NULL, '2021-04-02 14:45:40', NULL);

-- ----------------------------
-- Table structure for zs_user_mbpoit_record_detail
-- ----------------------------
DROP TABLE IF EXISTS `zs_user_mbpoit_record_detail`;
CREATE TABLE `zs_user_mbpoit_record_detail`  (
  `rdetailid` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备id	uuid',
  `mtbutch` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批次，每次新增时插入',
  `fromuserid` bigint(60) NOT NULL COMMENT '转出用户id',
  `touserid` bigint(60) NOT NULL COMMENT '转入用户',
  `mbpoint` decimal(20, 8) NOT NULL COMMENT '转让积分',
  `transfer_status` int(6) DEFAULT NULL COMMENT '转让状态 1 待转让 2 转让中 3、已转入 4转让失败',
  `transfertime` datetime(0) DEFAULT NULL COMMENT '分红时间	yyyy-MM-dd',
  `faild_msg` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '转让失败原因',
  `instime` datetime(0) DEFAULT NULL COMMENT '入库时间',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`rdetailid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
