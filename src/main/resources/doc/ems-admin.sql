/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80043
 Source Host           : 127.0.0.1:3306
 Source Schema         : ems-admin

 Target Server Type    : MySQL
 Target Server Version : 80043
 File Encoding         : 65001

 Date: 27/10/2025 22:03:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` varchar(2) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '日志类型（1正常 2错误）',
  `method` varchar(100) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '请求方式',
  `params` varchar(255) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '请求参数',
  `time` bigint DEFAULT NULL COMMENT '耗时（毫秒）',
  `ip` varchar(20) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT 'IP',
  `username` varchar(100) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '请求用户名',
  `exception_detail` text COLLATE utf8mb3_german2_ci COMMENT '错误信息详情',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `description` varchar(255) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8967 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(40) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父ID',
  `path` varchar(100) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT 'url',
  `type` varchar(2) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '类型（1菜单 2页面 3按钮）',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `component` varchar(100) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '组件',
  `permission` varchar(100) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, NULL, '1', 4, NULL, '2025-10-26 22:41:43', NULL, NULL, 'iconfont icon-setting');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, '/user', '2', 1, NULL, '2025-10-26 22:42:20', 'user/index.vue', NULL, 'iconfont icon-yonghuguanli_huaban');
INSERT INTO `sys_menu` VALUES (3, '菜单管理', 1, '/menu', '2', 2, '2021-08-18 21:26:01', '2025-10-26 22:42:10', 'menu/index.vue', NULL, 'iconfont icon-caidan');
INSERT INTO `sys_menu` VALUES (11, '日志管理', 0, NULL, '1', 3, '2021-08-23 22:00:33', '2025-10-26 22:41:34', NULL, NULL, 'iconfont icon-rizhi');
INSERT INTO `sys_menu` VALUES (12, '角色管理', 1, '/role', '2', 3, '2021-08-24 20:46:39', '2025-10-26 22:42:41', 'role/index.vue', NULL, 'iconfont icon-jiaoseguanli');
INSERT INTO `sys_menu` VALUES (17, '日志记录', 11, '/log', '2', 1, '2021-11-23 09:32:38', '2025-10-26 22:43:00', 'log/index.vue', NULL, 'iconfont icon-rizhi');
INSERT INTO `sys_menu` VALUES (20, '用户新增', 2, '/sys/user/edit', '3', 1, '2022-01-19 21:57:32', '2025-05-12 17:41:07', '/sys/user/edit', 'user:add', NULL);
INSERT INTO `sys_menu` VALUES (21, '用户修改', 2, '/sys/user/edit', '3', 2, '2022-01-19 22:04:41', NULL, '/sys/user/edit', 'user:edit', NULL);
INSERT INTO `sys_menu` VALUES (22, '用户删除', 2, '/sys/user/del', '3', 3, '2022-01-19 22:05:17', NULL, '/sys/user/del', 'user:del', NULL);
INSERT INTO `sys_menu` VALUES (23, '菜单新增', 3, '/sys/menu/edit', '3', 1, '2022-01-19 22:06:31', '2022-01-19 22:07:31', '/sys/menu/edit', 'menu:add', NULL);
INSERT INTO `sys_menu` VALUES (24, '菜单修改', 3, '/sys/menu/edit', '3', 2, '2022-01-19 22:06:53', NULL, '/sys/menu/edit', 'menu:edit', NULL);
INSERT INTO `sys_menu` VALUES (25, '菜单删除', 3, '/sys/menu/del', '3', 3, '2022-01-19 22:07:20', '2022-01-19 22:07:40', '/sys/menu/del', 'menu:del', NULL);
INSERT INTO `sys_menu` VALUES (26, '角色新增', 12, '/sys/role/edit', '3', 1, '2022-01-19 22:08:29', NULL, '/sys/role/edit', 'role:add', NULL);
INSERT INTO `sys_menu` VALUES (27, '角色修改', 12, '/sys/role/edit', '3', 2, '2022-01-19 22:09:06', NULL, '/sys/role/edit', 'role:edit', NULL);
INSERT INTO `sys_menu` VALUES (28, '角色授权', 12, 'sys/role/menu/edit', '3', 3, '2022-01-19 22:11:13', '2022-01-19 22:11:22', 'sys/role/menu/edit', 'role:authorize', NULL);
INSERT INTO `sys_menu` VALUES (29, '角色删除', 12, '/sys/role/del', '3', 4, '2022-01-19 22:11:52', NULL, '/sys/role/del', 'role:del', NULL);
INSERT INTO `sys_menu` VALUES (30, '用户列表', 2, '/sys/user/table', '3', 4, '2022-01-20 22:35:59', NULL, '/sys/user/table', 'user:list', NULL);
INSERT INTO `sys_menu` VALUES (31, '菜单列表', 3, '/sys/menu/table', '3', 4, '2022-01-20 22:36:59', NULL, '/sys/menu/table', 'menu:list', NULL);
INSERT INTO `sys_menu` VALUES (32, '角色列表', 12, '/sys/role/table', '3', 5, '2022-01-20 22:40:43', '2022-01-20 22:40:52', '/sys/role/table', 'role:list', NULL);
INSERT INTO `sys_menu` VALUES (34, '左侧菜单', 33, 'sys/menu/tree', '3', 1, '2022-01-20 23:18:03', '2022-01-20 23:20:35', 'sys/menu/tree', 'menu:tree', NULL);
INSERT INTO `sys_menu` VALUES (36, '启停用户', 2, '/sys/user/enabled', '3', 5, '2022-10-06 12:22:58', NULL, '', 'user:enabled', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(20) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '角色代码',
  `role_name` varchar(20) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '角色名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(200) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'ROLE_ADMIN', '超级管理员', '2021-08-25 22:55:45', '系统唯一超级管理员，可操作任意功能', NULL);
INSERT INTO `sys_role` VALUES (2, 'ROLE_USER', '普通用户', '2021-11-24 22:47:44', '普通用户', NULL);
INSERT INTO `sys_role` VALUES (3, 'ROLE_TEST', '测试用户', '2021-11-24 22:48:04', '测试用户', '2021-12-10 23:33:26');
INSERT INTO `sys_role` VALUES (6, '1', '1', '2025-05-06 17:00:50', '1', NULL);
INSERT INTO `sys_role` VALUES (7, '1213', '12123', '2025-05-22 13:44:10', '13131313', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (36, 3, 32, '2022-11-08 23:00:11', NULL);
INSERT INTO `sys_role_menu` VALUES (37, 3, 17, '2022-11-08 23:00:11', NULL);
INSERT INTO `sys_role_menu` VALUES (38, 3, 11, '2022-11-08 23:00:11', NULL);
INSERT INTO `sys_role_menu` VALUES (46, 6, 32, '2025-05-06 17:01:03', NULL);
INSERT INTO `sys_role_menu` VALUES (47, 6, 1, '2025-05-06 17:01:03', NULL);
INSERT INTO `sys_role_menu` VALUES (48, 6, 2, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (49, 6, 3, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (50, 6, 36, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (51, 6, 11, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (52, 6, 12, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (53, 6, 17, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (54, 6, 20, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (55, 6, 21, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (56, 6, 22, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (57, 6, 23, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (58, 6, 24, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (59, 6, 25, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (60, 6, 26, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (61, 6, 27, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (62, 6, 28, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (63, 6, 29, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (64, 6, 30, '2025-05-06 17:01:04', NULL);
INSERT INTO `sys_role_menu` VALUES (65, 6, 31, '2025-05-06 17:01:05', NULL);
INSERT INTO `sys_role_menu` VALUES (142, 2, 32, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (143, 2, 3, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (144, 2, 11, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (145, 2, 12, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (146, 2, 17, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (147, 2, 21, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (148, 2, 22, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (149, 2, 23, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (150, 2, 24, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (151, 2, 25, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (152, 2, 26, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (153, 2, 27, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (154, 2, 28, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (155, 2, 29, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (156, 2, 30, '2025-08-21 10:29:25', NULL);
INSERT INTO `sys_role_menu` VALUES (157, 2, 31, '2025-08-21 10:29:25', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_user` VALUES (1, 1, 1, '2021-11-17 23:00:35', NULL);
INSERT INTO `sys_role_user` VALUES (28, 10, 3, '2022-11-08 22:40:46', NULL);
INSERT INTO `sys_role_user` VALUES (29, 10, 2, '2022-11-08 22:40:46', NULL);
INSERT INTO `sys_role_user` VALUES (30, 11, 2, '2025-03-08 11:24:45', NULL);
INSERT INTO `sys_role_user` VALUES (33, 12, 2, '2025-03-26 22:03:03', NULL);
INSERT INTO `sys_role_user` VALUES (35, 13, 2, '2025-05-12 17:45:05', NULL);
INSERT INTO `sys_role_user` VALUES (37, 14, 3, '2025-06-11 22:51:12', NULL);
INSERT INTO `sys_role_user` VALUES (40, 15, 3, '2025-07-10 16:39:31', NULL);
INSERT INTO `sys_role_user` VALUES (41, 16, 3, '2025-08-16 16:45:13', NULL);
INSERT INTO `sys_role_user` VALUES (42, 2, 3, '2025-10-14 19:30:55', NULL);
INSERT INTO `sys_role_user` VALUES (43, 17, 2, '2025-10-14 19:34:45', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` varchar(100) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '邮箱',
  `username` varchar(50) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '密码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `enabled` bit(1) DEFAULT NULL COMMENT '状态（0停用 1启用）',
  `nick_name` varchar(50) COLLATE utf8mb3_german2_ci DEFAULT NULL COMMENT '昵称',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, '123@qq.com', 'admin', '$2a$10$7mEsufHTedaqOJzDXm/ABe.c3Sx8/ethdyaoCOEyA0x8gGqR3f1sK', '2021-12-01 21:09:15', b'1', '超级管理员', '2021-12-01 21:09:15');
INSERT INTO `sys_user` VALUES (2, NULL, 'user', '$2a$10$b5mQxndMAYD3cOnqVyENHOFCRu7lgOY0etmNT5JRtX9RGz.Bkh.jC', '2025-10-14 19:30:54', b'1', '普通用户', '2025-10-14 19:30:55');
INSERT INTO `sys_user` VALUES (10, NULL, 'test2', '$2a$10$EjKoqaTyrPpPAL7GYHH27.4iccEb4/J3NhQylM.SbeRCw59a5odRq', '2025-03-11 22:15:59', b'1', '测试用户', '2025-03-11 22:15:59');
INSERT INTO `sys_user` VALUES (11, NULL, 'wys', '$2a$10$i397uFgzKO4lWO6XJNZreuaPRrM.Ccq23IFes6sx9FoyaHM9rUwCa', '2025-08-20 11:42:50', b'1', '小白', '2025-08-20 11:42:51');
INSERT INTO `sys_user` VALUES (12, NULL, '1', '$2a$10$n66qLNKtsZ.bVbfIbN6vy.E3KQUQu5XAq4tUgN3202zUG1ztnaewi', '2025-08-20 11:42:49', b'0', '1', '2025-08-20 11:42:49');
INSERT INTO `sys_user` VALUES (13, NULL, 'guanliyuan2', '$2a$10$7nRVpJQaiupLckcINmcUjeuvNmRsnMVTX13TTDmRLHACTVAvpcDUS', '2025-05-23 17:29:28', b'1', '管理员2', '2025-05-23 17:29:29');
INSERT INTO `sys_user` VALUES (14, NULL, '111', '$2a$10$YsdnDekU3UfA7QxeXD.um.iLSaNCItULoX7S79Qd/4uZKqBRw4RMG', '2025-06-11 22:51:24', b'1', '111', '2025-06-11 22:51:24');
INSERT INTO `sys_user` VALUES (15, NULL, '232', '$2a$10$dw6qNt2RD0ekSm1HU7eeNuG7nca1/IJVy66qG3QrNJBmubXeKZc9a', '2025-07-10 16:39:31', NULL, '2342', NULL);
INSERT INTO `sys_user` VALUES (16, NULL, 'fei', '$2a$10$ewSVCSpsMLSzDMjn5DqCZObpK8uaCvQShqKzcQBwIYmu8F7FcI14O', '2025-08-16 16:45:13', NULL, 'fei', NULL);
INSERT INTO `sys_user` VALUES (17, NULL, 'we', '$2a$10$Q/8SAnDSw0CEuQFCER2I6eKon1TdKKUvX2gIvrAiNm0GQaYFydeOi', '2025-10-14 19:34:45', NULL, '23', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
