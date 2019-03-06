package com.yonyou.iuap.currtype.dao;
import com.yonyou.iuap.currtype.entity.Currtype;
import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericMapper;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@MyBatisRepository
public interface CurrtypeMapper extends GenericExMapper<Currtype> {

    List<Currtype> getByIds(String tenantId, @Param("list") List<String> ids);

    List<Currtype> queryList4(@Param("condition") Map<String, Object> params);
}

