package com.yonyou.iuap.order_info.service;
import com.yonyou.iuap.baseservice.intg.service.GenericIntegrateService;
import com.yonyou.iuap.baseservice.intg.support.ServiceFeature;
import com.yonyou.iuap.order_info.dao.OrderInfoNewMapper;
import com.yonyou.iuap.order_info.entity.OrderInfoNew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.ref.service.RefCommonService;
import java.util.List;

import static com.yonyou.iuap.baseservice.intg.support.ServiceFeature.*;
@Service

/**
 * OrderInfoNew CRUD 核心服务,提供逻辑删除/乐观锁
 */
public class OrderInfoNewService extends GenericIntegrateService<OrderInfoNew>{


    private OrderInfoNewMapper orderInfoNewMapper;

    @Autowired
    public void setOrderInfoNewMapper(OrderInfoNewMapper orderInfoNewMapper) {
        this.orderInfoNewMapper = orderInfoNewMapper;
        super.setGenericMapper(orderInfoNewMapper);
    }
    
        @Autowired
    private RefCommonService refService;
    @Autowired
    private OrderInfoNewEnumService OrderInfoNewEnumService;
        public List selectListByExcelData(List idsList) {
                List list  = orderInfoNewMapper.selectListByExcelData(idsList);
                list = refService.fillListWithRef(list);
                list = OrderInfoNewEnumService.afterListQuery(list);
                return list;
        }


    /**
     * @CAU 可插拔设计
     * @return 向父类 GenericIntegrateService 提供可插拔的特性声明
     */
    @Override
    protected ServiceFeature[] getFeats() {
        return new ServiceFeature[]{ REFERENCE,BPM,MULTI_TENANT,LOGICAL_DEL };
    }
}