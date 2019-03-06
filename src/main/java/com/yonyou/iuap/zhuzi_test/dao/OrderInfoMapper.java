package com.yonyou.iuap.zhuzi_test.dao;
import com.yonyou.iuap.zhuzi_test.entity.OrderInfo;
import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericMapper;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;


@MyBatisRepository
public interface OrderInfoMapper extends GenericExMapper<OrderInfo> {
}

