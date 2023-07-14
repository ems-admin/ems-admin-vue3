/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : ems-admin

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 09/07/2023 22:10:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_german2_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (23);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '日志类型（1正常 2错误）',
  `method` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '请求方式',
  `params` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint NULL DEFAULT NULL COMMENT '耗时（毫秒）',
  `ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT 'IP',
  `username` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '请求用户名',
  `exception_detail` text CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL COMMENT '错误信息详情',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6730 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_german2_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '名称',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父ID',
  `path` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT 'url',
  `type` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '类型（1菜单 2页面 3按钮）',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `component` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '组件',
  `permission` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_german2_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, NULL, '1', 1, NULL, '2022-10-11 23:00:32', NULL, NULL, 'iconfont icon-setting');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, '/user', '2', 1, NULL, '2022-10-11 22:39:19', 'user/index', NULL, 'iconfont icon-yonghuguanli_huaban');
INSERT INTO `sys_menu` VALUES (3, '菜单管理', 1, '/menu', '2', 2, '2021-08-18 21:26:01', '2022-10-11 22:39:28', 'menu/index', NULL, 'iconfont icon-caidan');
INSERT INTO `sys_menu` VALUES (11, '日志管理', 0, NULL, '1', 3, '2021-08-23 22:00:33', '2022-10-11 21:27:21', NULL, NULL, 'iconfont icon-rizhi');
INSERT INTO `sys_menu` VALUES (12, '角色管理', 1, '/role', '2', 3, '2021-08-24 20:46:39', '2022-10-11 22:39:33', 'role/index', NULL, 'iconfont icon-jiaoseguanli');
INSERT INTO `sys_menu` VALUES (17, '日志记录', 11, '/log', '2', 1, '2021-11-23 09:32:38', '2022-11-10 21:33:05', 'log/index', NULL, 'iconfont icon-rizhi');
INSERT INTO `sys_menu` VALUES (20, '用户新增', 2, '/sys/user/edit', '3', 1, '2022-01-19 21:57:32', '2022-01-19 22:03:54', '/sys/user/edit', 'user:add', NULL);
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

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '角色代码',
  `role_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_german2_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ROLE_ADMIN', '超级管理员', '2021-08-25 22:55:45', '系统唯一超级管理员，可操作任意功能', NULL);
INSERT INTO `sys_role` VALUES (2, 'ROLE_USER', '普通用户', '2021-11-24 22:47:44', '普通用户', NULL);
INSERT INTO `sys_role` VALUES (3, 'ROLE_TEST', '测试用户', '2021-11-24 22:48:04', '测试用户', '2021-12-10 23:33:26');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_german2_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (33, 2, 17, '2022-10-06 12:14:36', NULL);
INSERT INTO `sys_role_menu` VALUES (34, 2, 11, '2022-10-06 12:14:36', NULL);
INSERT INTO `sys_role_menu` VALUES (35, 2, 30, '2022-10-06 12:14:36', NULL);
INSERT INTO `sys_role_menu` VALUES (36, 3, 32, '2022-11-08 23:00:11', NULL);
INSERT INTO `sys_role_menu` VALUES (37, 3, 17, '2022-11-08 23:00:11', NULL);
INSERT INTO `sys_role_menu` VALUES (38, 3, 11, '2022-11-08 23:00:11', NULL);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_german2_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1, 1, '2021-11-17 23:00:35', NULL);
INSERT INTO `sys_role_user` VALUES (21, 2, 2, '2021-12-16 21:51:05', NULL);
INSERT INTO `sys_role_user` VALUES (28, 10, 3, '2022-11-08 22:40:46', NULL);
INSERT INTO `sys_role_user` VALUES (29, 10, 2, '2022-11-08 22:40:46', NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '邮箱',
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `enabled` bit(1) NULL DEFAULT NULL COMMENT '状态（0停用 1启用）',
  `nick_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NULL DEFAULT NULL COMMENT '昵称',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_german2_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '123@qq.com', 'admin', '$2a$10$mRg0FgMEWxi5d5NVdjiShOEb7yn3/ZnkidqAZOc0Kgw1IHtzE0F9q', '2023-01-01 00:19:38', b'1', '超级管理员', NULL);
INSERT INTO `sys_user` VALUES (2, NULL, 'user', '$2a$10$b5mQxndMAYD3cOnqVyENHOFCRu7lgOY0etmNT5JRtX9RGz.Bkh.jC', '2021-12-16 21:51:15', b'1', '普通用户', '2021-12-16 21:51:16');
INSERT INTO `sys_user` VALUES (10, NULL, 'test2', '$2a$10$EjKoqaTyrPpPAL7GYHH27.4iccEb4/J3NhQylM.SbeRCw59a5odRq', '2023-07-04 22:03:41', b'0', '测试用户', '2023-07-04 22:03:42');

SET FOREIGN_KEY_CHECKS = 1;
