DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `order_type` varchar(64) DEFAULT NULL COMMENT '订单类型',
  `order_no` varchar(64) DEFAULT NULL COMMENT '编号',
  `pur_org` varchar(64) DEFAULT NULL COMMENT '采购单位',
  `release_time` varchar(64) DEFAULT NULL COMMENT '发布时间',
  `order_amount` decimal(20,0) DEFAULT NULL COMMENT '订单金额',
  `apply_no` varchar(64) DEFAULT NULL COMMENT '供应商编号',
  `pur_group_no` varchar(64) DEFAULT NULL COMMENT '采购组编号',
  `confirm_time` varchar(64) DEFAULT NULL COMMENT '确认时间',
  `order_state` varchar(64) DEFAULT NULL COMMENT '订单状态',
  `TENANT_ID` varchar(64) DEFAULT NULL,
  `DR` int(11) DEFAULT NULL COMMENT '是否删除',
  `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
  `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
  `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `currtype`;
CREATE TABLE `currtype` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `TENANT_ID` varchar(64) DEFAULT NULL,
  `DR` int(11) DEFAULT NULL COMMENT '是否删除',
  `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
  `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
  `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of currtype
-- ----------------------------
BEGIN;
INSERT INTO `currtype` VALUES ('1', 'RMB', '人民币', 'tenant', 0, '1', '1', '2', '3', '4', '2019-03-05 22:16:19');
INSERT INTO `currtype` VALUES ('2', 'dollar', '美元', 'tenant', 0, '2', '1', '2', '3', '4', '2019-03-05 22:16:22');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

drop table if EXISTS order_info_new;
CREATE TABLE `order_info_new` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `order_type` VARCHAR(64) DEFAULT NULL COMMENT '订单类型',
    `order_no` VARCHAR(64) DEFAULT NULL COMMENT '编号',
    `pur_org` VARCHAR(64) DEFAULT NULL COMMENT '采购单位',
    `release_time` VARCHAR(64) DEFAULT NULL COMMENT '发布时间',
    `order_amount` NUMERIC(20) DEFAULT NULL COMMENT '订单金额',
    `pur_group_no` VARCHAR(64) DEFAULT NULL COMMENT '采购组编号',
    `confirm_time` VARCHAR(64) DEFAULT NULL COMMENT '确认时间',
    `order_state` VARCHAR(64) DEFAULT NULL COMMENT '订单状态',
        `BPM_STATE` int(1) DEFAULT NULL,
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

alter table order_info_new add unique(order_no);