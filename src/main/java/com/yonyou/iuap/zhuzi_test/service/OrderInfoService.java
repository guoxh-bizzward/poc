package com.yonyou.iuap.zhuzi_test.service;
import com.yonyou.iuap.base.utils.CommonConstants;
import com.yonyou.iuap.baseservice.intg.service.GenericIntegrateService;
import com.yonyou.iuap.baseservice.intg.support.ServiceFeature;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.zhuzi_test.dao.OrderInfoMapper;
import com.yonyou.iuap.zhuzi_test.entity.OrderInfo;

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
 * OrderInfo CRUD 核心服务,提供逻辑删除/乐观锁
 */
public class OrderInfoService extends GenericIntegrateService<OrderInfo>{


    private OrderInfoMapper orderInfoMapper;

    @Autowired
    public void setOrderInfoMapper(OrderInfoMapper orderInfoMapper) {
        this.orderInfoMapper = orderInfoMapper;
        super.setGenericMapper(orderInfoMapper);
    }

    @Override
    public Page<OrderInfo> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        searchParams.addCondition("sql",buildPermSql());
        return super.selectAllByPage(pageRequest, searchParams);
    }

    /**
     * @CAU 可插拔设计
     * @return 向父类 GenericIntegrateService 提供可插拔的特性声明
     */
    @Override
    protected ServiceFeature[] getFeats() {
        return new ServiceFeature[]{ REFERENCE,MULTI_TENANT,LOGICAL_DEL };
    }


    public String buildPermSql() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(String.format(" and %s='%s'", CommonConstants.TENANT_FiELEDNAME, InvocationInfoProxy.getTenantid()));

        HashMap<String, String> fieldDataPermResTypeMap = processFieldDataPermResTypeMap();

        Set<String> keySet = fieldDataPermResTypeMap.keySet();
        Map<String, Set<String>> resTypeDataPermMap = new HashMap<String, Set<String>>();

        for (String columnname : keySet) {
            String resourceTypeCode = fieldDataPermResTypeMap.get(columnname);
            Set<String> set = resTypeDataPermMap.get(resourceTypeCode);

            if(set == null){
                set = getAuthData(resourceTypeCode);
                resTypeDataPermMap.put(resourceTypeCode, set);
            }

            if(!set.isEmpty()){
                sqlBuilder.append(" and ").append(columnname).append(" in (");

                for (String param : set) {
                    sqlBuilder.append(String.format("'%s',", param));
                }

                sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
                sqlBuilder.append(")");
            }
        }

        String querySql = sqlBuilder.toString();

        return querySql;
    }
    /**
     * 需要支持数据权限的资源
     * @return
     */
    private HashMap<String, String> processFieldDataPermResTypeMap(){
        HashMap<String, String> fieldDataPermResTypeMap = new HashMap<String, String>();
        fieldDataPermResTypeMap.put("apply_no","bd_common_currency_ref"); //数据库字段与权限资源名称的对应关系
        return fieldDataPermResTypeMap;
    }
    /**
     * 调用API获取有权限的数据
     * @param resourceTypeCode
     * @return
     */
    private Set<String> getAuthData(String resourceTypeCode) {
        String tenantId = InvocationInfoProxy.getTenantid();
        String sysId = InvocationInfoProxy.getSysid();
        String userId = InvocationInfoProxy.getUserid();

        try {
            List<DataPermission> dataPerms = AuthRbacClient.getInstance().queryDataPerms(tenantId, sysId, userId, resourceTypeCode);
            Set<String> set = new HashSet<String>();
            for (Iterator<DataPermission> iterator = dataPerms.iterator(); iterator.hasNext();) {
                DataPermission dataPermission = (DataPermission) iterator.next();
                set.add(dataPermission.getResourceId());
            }

            return set;
        } catch (BusinessException e) {
            e.printStackTrace();

            return null;
        }

    }
}