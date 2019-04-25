package com.yonyou.iuap.order_info.dao;
import com.yonyou.iuap.order_info.entity.OrderInfoNew;
import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericMapper;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;
import java.util.List;


@MyBatisRepository
public interface OrderInfoNewMapper extends GenericExMapper<OrderInfoNew> {
        List selectListByExcelData(List list);
}

