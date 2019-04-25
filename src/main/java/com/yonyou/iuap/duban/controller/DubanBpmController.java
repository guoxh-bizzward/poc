package com.yonyou.iuap.duban.controller;

import com.yonyou.iuap.baseservice.bpm.controller.GenericBpmController;
import com.yonyou.iuap.duban.entity.Duban;
import com.yonyou.iuap.duban.service.DubanBpmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 说明：平台-督办任务主表 流程控制Controller——提供流程提交、收回、审批回调等rest接口
 * 
 * @date 2019-3-15 10:25:42
 */
@Controller
@RequestMapping(value="/DUBAN")
public class DubanBpmController extends GenericBpmController<Duban>{
    
    private Logger logger = LoggerFactory.getLogger(DubanController.class);


    private DubanBpmService service;
    @Autowired
    public void setService(DubanBpmService service) {
        this.service = service;
        super.setService(service);
    }


}
