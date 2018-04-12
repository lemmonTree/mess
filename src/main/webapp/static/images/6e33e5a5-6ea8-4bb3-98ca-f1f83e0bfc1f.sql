CREATE TABLE `z_pay_order_manual` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `channel` varchar(3) DEFAULT NULL COMMENT '渠道：1.聪头 2.大智慧',
  `productType` varchar(3) DEFAULT NULL COMMENT '产品类型 5.金三顺 0.诊股 4.板块轮动',
  `out_trade_code` varchar(32) DEFAULT NULL COMMENT '订单号',
  `phone` varchar(32) DEFAULT NULL COMMENT '用户手机号',
  `saler` int(11) DEFAULT NULL COMMENT '销售人员',
  `shouldPay` int(11) DEFAULT NULL COMMENT '应付金额',
  `discountPay` int(11) DEFAULT NULL COMMENT '折扣后金额',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `outTradeCode` (`out_trade_code`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;




INSERT INTO `oss_permission` VALUES ('90312', '新增订单列表', 'user.createorder');
INSERT INTO `oss_permission` VALUES ('90313', '全部订单列表', 'user.allorder');
INSERT INTO `oss_permission` VALUES ('90314', '聪头订单列表', 'user.ctorder');
INSERT INTO `oss_permission` VALUES ('90315', '爱股票订单列表', 'user.igporder');
INSERT INTO `oss_permission` VALUES ('90316', '大智慧订单列表', 'user.dzhorder');
INSERT INTO `oss_permission` VALUES ('11', '大智慧管理', 'dzh.manager');
