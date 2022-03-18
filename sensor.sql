/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : sensor

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 17/03/2022 17:31:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_component
-- ----------------------------
DROP TABLE IF EXISTS `t_component`;
CREATE TABLE `t_component`  (
  `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `component_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `count` int(11) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_component_id`(`component_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_component
-- ----------------------------
INSERT INTO `t_component` VALUES (1, 'LBJ_010', '驱动轮对', 1, NULL);
INSERT INTO `t_component` VALUES (2, 'LBJ_011', '轴箱', 1, NULL);
INSERT INTO `t_component` VALUES (3, 'LBJ_012', '一系悬挂', 1, NULL);
INSERT INTO `t_component` VALUES (4, 'LBJ_013', '构架', 1, NULL);
INSERT INTO `t_component` VALUES (5, 'LBJ_014', '二系悬挂', 1, NULL);
INSERT INTO `t_component` VALUES (6, 'LBJ_015', '横向液压减震器', 1, NULL);
INSERT INTO `t_component` VALUES (7, 'LBJ_016', '驱动装置牵引电机', 1, NULL);
INSERT INTO `t_component` VALUES (8, 'LBJ_017', '构架', 1, NULL);
INSERT INTO `t_component` VALUES (9, 'LBJ_018', '盘形制动', 1, NULL);
INSERT INTO `t_component` VALUES (10, 'LBJ_009', '二系悬挂高度调整阀门', 1, NULL);
INSERT INTO `t_component` VALUES (11, 'LBJ_001', '轴箱抗蛇形减震器', 1, NULL);
INSERT INTO `t_component` VALUES (12, 'LBJ_002', '锥齿轮轴滚子轴承1', 3, NULL);
INSERT INTO `t_component` VALUES (13, 'LBJ_003', '轮对轴配滚子轴承2', 5, NULL);
INSERT INTO `t_component` VALUES (14, 'LBJ_004', '轮对轴滚子轴承3', 7, NULL);
INSERT INTO `t_component` VALUES (15, 'LBJ_005', '轮对轴大齿轮', 3, NULL);
INSERT INTO `t_component` VALUES (16, 'LBJ_006', '锥齿轮', 4, NULL);
INSERT INTO `t_component` VALUES (17, 'LBJ_007', '万向轴', 1, NULL);

-- ----------------------------
-- Table structure for t_measure
-- ----------------------------
DROP TABLE IF EXISTS `t_measure`;
CREATE TABLE `t_measure`  (
  `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `measure_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `current_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `measure_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `frequency` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_measure_id`(`measure_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_measure
-- ----------------------------
INSERT INTO `t_measure` VALUES (1, 'Data_1', '运行速度', '速度', '100 km/h', '0-350 km/h', NULL, NULL);
INSERT INTO `t_measure` VALUES (2, 'Data_2', '车轴转速', '速度', '5589 r/min', '0-12000 r/min', NULL, NULL);
INSERT INTO `t_measure` VALUES (3, 'Data_3', '发电机转速', '速度', '1832 r/min', '0-3600 r/min', NULL, NULL);
INSERT INTO `t_measure` VALUES (4, 'Data_4', '轴箱温度', '温度', '30°C', '50°C', NULL, NULL);
INSERT INTO `t_measure` VALUES (5, 'Data_5', '齿轮箱温度', '温度', '30°C', '50°C', NULL, NULL);
INSERT INTO `t_measure` VALUES (6, 'Data_6', '牵引电机温度', '温度', '30°C', '50°C', NULL, NULL);
INSERT INTO `t_measure` VALUES (7, 'Data_7', '环境温度', '温度', '25°C', '50°C', NULL, NULL);
INSERT INTO `t_measure` VALUES (8, 'Data_9', '发电机输出电压', '电压', '58.9 v', '50 v', NULL, NULL);
INSERT INTO `t_measure` VALUES (9, 'Data_10', '转向架轴距', '位移', '2200 mm', '2200 mm', NULL, NULL);
INSERT INTO `t_measure` VALUES (10, 'Data_11', '轮对内侧距', '位移', '1353 mm', '1353（±2）mm', NULL, NULL);
INSERT INTO `t_measure` VALUES (11, 'Data_12', '弹簧高度', '位移', '251 mm', '251mm+-2', NULL, NULL);
INSERT INTO `t_measure` VALUES (12, 'Data_13', '加载后高度', '位移', '228 mm', '228+-2', NULL, NULL);
INSERT INTO `t_measure` VALUES (13, 'Data_14', '下垂向刚度', '应力', '740 N/mm', '740N/mm', NULL, NULL);
INSERT INTO `t_measure` VALUES (14, 'Data_15', '下横向刚度', '应力', '2600 mm', '2600 mm', NULL, NULL);
INSERT INTO `t_measure` VALUES (15, 'Data_16', '下纵向刚度', '应力', '3910 mm', '3910 mm', NULL, NULL);

-- ----------------------------
-- Table structure for t_sensor
-- ----------------------------
DROP TABLE IF EXISTS `t_sensor`;
CREATE TABLE `t_sensor`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sensor_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `power` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `frequency` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `measure_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_sensor_id`(`sensor_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sensor
-- ----------------------------
INSERT INTO `t_sensor` VALUES (1, 'FA-03/04', '霍尔式电流型轴端速度传感器', '速度', '－40℃～+105℃', '+12VDC—+16VDC', '0—20kHz', 'Data_1', NULL, NULL);
INSERT INTO `t_sensor` VALUES (2, 'FA-05/06', '霍尔式电流型轴端速度传感器', '速度', '+12VDC—+16VDC', '+12VDC—+16VDC', '0—20kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (3, 'FA-01/02', '霍尔式电流型轴端速度传感器', '速度', '－40℃～+125℃', '+12VDC—+30VDC', '0—10kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (4, 'WZP-F4002', '齿轮箱温度传感器1', '温度', '-25℃～＋40℃', '+12VDC—+31VDC', '0—11kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (5, 'WZP-F4001', '齿轮箱温度传感器2', '温度', '-25℃～＋40℃', '+12VDC—+32VDC', '0—12kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (6, 'WZP-F2001', '齿轮箱温度传感器3', '温度', '-25℃～＋40℃', '+12VDC—+33VDC', '0—13kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (7, 'WZP-F1015', '轴端温度传感器', '温度', '-25℃～＋40℃', '+12VDC—+34VDC', '0—14kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (8, 'WZP-F1004', '轴端温度传感器', '温度', '25℃～＋40℃', '+12VDC—+35VDC', '0—15kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (9, 'DPM-950', '动态应变放大器', '应力', '25℃～＋41℃', '+12VDC—+36VDC', '0—16kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (10, 'AGC-876', '光电式车速传感器', '速度', '25℃～＋42℃', '+12VDC—+37VDC', '0—17kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (11, 'SGG-88', '磁电式车速传感器', '速度', '25℃～＋43℃', '+12VDC—+38VDC', '0—18kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (12, 'GDH-709', '霍尔式车速传感器', '速度', '25℃～＋44℃', '+12VDC—+39VDC', '0—19kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (13, 'SDF-13', '红外轴温传感器', '温度', '25℃～＋45℃', '+12VDC—+40VDC', '0—20kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (14, 'GAT-831', '加速度传感器', '加速度', '25℃～＋46℃', '+12VDC—+41VDC', '0—21kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (15, 'FJF-443', '振动传感器', '振动', '25℃～＋47℃', '+12VDC—+42VDC', '0—22kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (16, 'BSX-490', '位移传感器', '位移', '25℃～＋48℃', '+12VDC—+43VDC', '0—23kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (17, 'SDF-193', '湿度传感器', '湿度', '25℃～＋49℃', '+12VDC—+44VDC', '0—24kHz', NULL, NULL, NULL);
INSERT INTO `t_sensor` VALUES (18, 'FJF-843', '声音传感器', '声音', '25℃～＋50℃', '+12VDC—+45VDC', '0—25kHz', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '123456');
INSERT INTO `t_user` VALUES (2, 'ysr', '123');

SET FOREIGN_KEY_CHECKS = 1;
