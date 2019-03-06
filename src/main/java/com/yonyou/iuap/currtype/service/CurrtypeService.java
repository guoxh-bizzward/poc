package com.yonyou.iuap.currtype.service;
import com.yonyou.iuap.base.utils.CommonConstants;
import com.yonyou.iuap.baseservice.intg.service.GenericIntegrateService;
import com.yonyou.iuap.baseservice.intg.support.ServiceFeature;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.currtype.dao.CurrtypeMapper;
import com.yonyou.iuap.currtype.entity.Currtype;

import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.exception.BusinessException;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.*;

import static com.yonyou.iuap.baseservice.intg.support.ServiceFeature.*;
@Service

/**
 * Currtype CRUD 核心服务,提供逻辑删除/乐观锁
 */
public class CurrtypeService extends GenericIntegrateService<Currtype>{


    private CurrtypeMapper currtypeMapper;

    @Autowired
    public void setCurrtypeMapper(CurrtypeMapper currtypeMapper) {
        this.currtypeMapper = currtypeMapper;
        super.setGenericMapper(currtypeMapper);
    }

    @Override
    public Page<Currtype> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
       // searchParams.addCondition("sql",buildPermSql());
        return super.selectAllByPage(pageRequest, searchParams);
    }
    public List<Currtype> getCurrtypeByIds(String[] strArray) {

        String tenantId = InvocationInfoProxy.getTenantid();
        ArrayList<String> ids = new ArrayList<String>();
        for (String key : strArray) {
            ids.add(key);
        }
        return currtypeMapper.getByIds(tenantId, ids);
    }

    public List<Currtype> getByIds(String tenantId, List<String> ids) {
        if(ids==null||ids.size()==0){
            return null;
        }
        return currtypeMapper.getByIds(tenantId, ids);
    }

    public List<Currtype> query4Refer(String refParam) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("refParam", refParam);
        return currtypeMapper.queryList4(params);
    }
    /**
     * @CAU 可插拔设计
     * @return 向父类 GenericIntegrateService 提供可插拔的特性声明
     */
    @Override
    protected ServiceFeature[] getFeats() {
        return new ServiceFeature[]{ REFERENCE,MULTI_TENANT,LOGICAL_DEL };
    }


}