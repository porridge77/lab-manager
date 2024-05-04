/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : lab_manager

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 04/05/2024 16:12:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '123', '李中', 'ADMIN');

-- ----------------------------
-- Table structure for fix
-- ----------------------------
DROP TABLE IF EXISTS `fix`;
CREATE TABLE `fix`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `teacher_id` int(0) NULL DEFAULT NULL COMMENT '报修教师',
  `lab_id` int(0) NULL DEFAULT NULL COMMENT '实验室ID',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '故障描述',
  `status` enum('已维修','未维修','维修中') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '未维修' COMMENT '处理状态',
  `date` date NULL DEFAULT NULL COMMENT '报修日期',
  `statement` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修情况说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '报修信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fix
-- ----------------------------
INSERT INTO `fix` VALUES (1, 6, 1, '1 computer is broken', '维修中', '2024-04-29', '');
INSERT INTO `fix` VALUES (8, 4, 1, '电脑坏了', '未维修', '2024-04-29', NULL);

-- ----------------------------
-- Table structure for lab
-- ----------------------------
DROP TABLE IF EXISTS `lab`;
CREATE TABLE `lab`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '实验室id',
  `no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '实验室编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `type` enum('软件','硬件','网络') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型',
  `equipment_num` int(0) NOT NULL COMMENT '设备数',
  `admin_id` int(0) NOT NULL COMMENT '管理员',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL DEFAULT '空闲' COMMENT '使用状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_vi_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lab
-- ----------------------------
INSERT INTO `lab` VALUES (1, '037', '计算机网络实验室', '网络', 120, 6, '空闲');

-- ----------------------------
-- Table structure for lab_admin
-- ----------------------------
DROP TABLE IF EXISTS `lab_admin`;
CREATE TABLE `lab_admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账户',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '实验室管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lab_admin
-- ----------------------------
INSERT INTO `lab_admin` VALUES (6, '211', '123456', '黄', 'LABADMIN', '助教');

-- ----------------------------
-- Table structure for lesson
-- ----------------------------
DROP TABLE IF EXISTS `lesson`;
CREATE TABLE `lesson`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `semester_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '学期',
  `teacher_id` int(0) NOT NULL COMMENT '申请教师id',
  `lab_id` int(0) NOT NULL COMMENT '实验室id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '课程名称',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '学生班级',
  `week` int(0) NOT NULL COMMENT '周数',
  `section` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL DEFAULT '' COMMENT '节次',
  `day_of_week` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '星期几',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_vi_0900_ai_ci COMMENT = '排课表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lesson
-- ----------------------------
INSERT INTO `lesson` VALUES (27, '2023-2024-2', 4, 1, '计算机网络', '21计机2班', 1, '1-2', '星期一');
INSERT INTO `lesson` VALUES (28, '2023-2024-2', 4, 1, '计算机网络', '21计机2班', 2, '1-2', '星期一');
INSERT INTO `lesson` VALUES (29, '2023-2024-2', 4, 1, '计算机网络', '21计机2班', 3, '1-2', '星期一');

-- ----------------------------
-- Table structure for semester
-- ----------------------------
DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '学期ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NOT NULL DEFAULT '2023-2024-2' COMMENT '学期名',
  `week_count` int(0) NOT NULL DEFAULT 20 COMMENT '周数',
  `is_current` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_vi_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of semester
-- ----------------------------
INSERT INTO `semester` VALUES (1, '2023-2024-2', 20, 1);
INSERT INTO `semester` VALUES (2, '2023-2024-1', 18, 0);

-- ----------------------------
-- Table structure for stu_reserve
-- ----------------------------
DROP TABLE IF EXISTS `stu_reserve`;
CREATE TABLE `stu_reserve`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `semester_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '学期',
  `stu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '申请学生id',
  `week` int(0) NOT NULL COMMENT '周次',
  `section` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '节次',
  `lab_id` int(0) NOT NULL COMMENT '实验室id',
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NULL COMMENT '原因',
  `date` date NULL DEFAULT NULL COMMENT '预约日期',
  `status` enum('未审核','通过','驳回','使用完毕') CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NULL DEFAULT '未审核' COMMENT '预约状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_vi_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_reserve
-- ----------------------------
INSERT INTO `stu_reserve` VALUES (1, '2023-2024-2', '1', 12, '3-4', 1, 'for traning', '2024-04-26', '通过');
INSERT INTO `stu_reserve` VALUES (2, '2023-2024-2', '1', 4, '4-5', 1, 'test', '2024-04-27', '未审核');
INSERT INTO `stu_reserve` VALUES (3, '2023-2024-2', '1', 12, '3-4', 1, 'test', '2024-04-29', '未审核');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名字',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '专业',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '班级',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '007', '123456', '刘', '计算机', '3', 'STUDENT');
INSERT INTO `student` VALUES (2, '0805', '123456', '王', '会计', '2', 'STUDENT');

-- ----------------------------
-- Table structure for tea_apply
-- ----------------------------
DROP TABLE IF EXISTS `tea_apply`;
CREATE TABLE `tea_apply`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `semester_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '学期',
  `teacher_id` int(0) NOT NULL COMMENT '教师ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '课程名称',
  `type` enum('软件','硬件','网络') CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '实验室类型',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '学生班级',
  `number` int(0) NOT NULL COMMENT '学生人数',
  `start` int(0) NOT NULL COMMENT '起始周',
  `end` int(0) NOT NULL COMMENT '结束周',
  `day_of_week` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '星期几',
  `section` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL COMMENT '节次',
  `status` enum('已排课','未排课') CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_ai_ci NOT NULL DEFAULT '未排课' COMMENT '排课状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_vi_0900_ai_ci COMMENT = '教师申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tea_apply
-- ----------------------------
INSERT INTO `tea_apply` VALUES (1, '2023-2024-2', 4, '计算机网络', '网络', '21计机2班', 30, 1, 3, '星期一', '1-2', '已排课');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账户',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职称',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '教师表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (4, '985', '123456', '许', '副教授', 'TEACHER');

SET FOREIGN_KEY_CHECKS = 1;
