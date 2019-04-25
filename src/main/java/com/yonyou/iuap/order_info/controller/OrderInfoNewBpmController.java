package com.yonyou.iuap.order_info.controller;

import com.yonyou.iuap.baseservice.bpm.controller.GenericBpmController;
import com.yonyou.iuap.order_info.entity.OrderInfoNew;
import com.yonyou.iuap.order_info.service.OrderInfoNewBpmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 说明：orderinfo_new 流程控制Controller——提供流程提交、收回、审批回调等rest接口
 * 
 * @date 2019-4-17 21:33:43
 */
@Controller
@RequestMapping(value="/order_info_new")
public class OrderInfoNewBpmController extends GenericBpmController<OrderInfoNew>{
    
    private Logger logger = LoggerFactory.getLogger(OrderInfoNewController.class);


    private OrderInfoNewBpmService service;
    @Autowired
    public void setService(OrderInfoNewBpmService service) {
        this.service = service;
        super.setService(service);
    }

}
