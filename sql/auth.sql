/*
Navicat MySQL Data Transfer
Source Server         : mysql80
Source Server Version : 80036
Source Host           : localhost:3306
Source Database       : auth
Target Server Type    : MYSQL
Target Server Version : 80036
File Encoding         : UTF-8
Date: 2026-01-12 14:34:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `auth`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `auth`;

-- ----------------------------
-- Table structure for `auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                             `username` varchar(50) NOT NULL COMMENT '用户名',
                             `password` varchar(255) NOT NULL COMMENT '密码（加密后）',
                             `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                             `role` varchar(50) NOT NULL COMMENT '角色',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_username` (`username`),
                             UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES
                            (1, 'user_1', '$2a$10$3t3UIbkwtwsnzqmrt7/a6uoAvloR1DN52cNKFE1C7vMCPJwr6Upu.', 'user_1@example.com', 'COMMON'),
                            (2, 'editor_1', '$2a$10$nl.bKP9c9cqQOO2h9m0Iruq8KEr7Gg2jDQIBdZRB98Lo9TEzVnX8i', 'editor_1@example.com', 'EDITOR'),
                            (3, 'adm_1', '$2a$10$oRtTdHvnGQHuWzbQl4kcp.Vbc7quLpfZ28EL5R4inqr0IGVmtli2S', 'adm_1@example.com', 'ADMIN');

-- ----------------------------
-- Table structure for `github_user`
-- ----------------------------
DROP TABLE IF EXISTS `github_user`;
CREATE TABLE `github_user` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                               `github_id` bigint NOT NULL COMMENT 'GitHub 用户ID',
                               `username` varchar(50) NOT NULL COMMENT '用户名',
                               `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                               `role` varchar(50) NOT NULL COMMENT '角色',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_github_id` (`github_id`),
                               UNIQUE KEY `uk_username` (`username`),
                               UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='GitHub 用户表';

-- ----------------------------
-- Records of github_user
-- ----------------------------
INSERT INTO `github_user` VALUES
    (1, 170590674, 'lhbchampion', NULL, 'EDITOR');

-- ----------------------------
-- Table structure for `ldap_user`
-- ----------------------------
DROP TABLE IF EXISTS `ldap_user`;
CREATE TABLE `ldap_user` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `username` varchar(50) NOT NULL COMMENT 'LDAP 登录名',
                             `name` varchar(100) DEFAULT NULL COMMENT '显示名',
                             `password` varchar(255) NOT NULL COMMENT '密码（加密后）',
                             `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                             `role` varchar(50) NOT NULL COMMENT '角色',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_username` (`username`),
                             UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='LDAP 用户表';

-- ----------------------------
-- Records of ldap_user
-- ----------------------------
INSERT INTO `ldap_user` VALUES
                            (1, 'ldap_user_1', 'LDAP 普通用户', '0f51fa2a-74d7-49ad-ab42-26f1c5dfe07a', 'ldap_user_1@example.com', 'COMMON'),
                            (2, 'ldap_editor_1', 'LDAP 编辑员', '0f51fa2a-74d7-49ad-ab42-26f1c5dfe07a', 'ldap_editor_1@example.com', 'EDITOR'),
                            (3, 'ldap_adm_1', 'LDAP 管理员', '0f51fa2a-74d7-49ad-ab42-26f1c5dfe07a', 'ldap_adm_1@example.com', 'ADMIN');
