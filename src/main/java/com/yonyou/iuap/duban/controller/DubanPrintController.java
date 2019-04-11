package com.yonyou.iuap.duban.controller;
import com.yonyou.iuap.baseservice.print.controller.GenericPrintController;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.baseservice.entity.annotation.Associative;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
   
import com.yonyou.iuap.duban.entity.DubanSub;
import com.yonyou.iuap.duban.service.DubanSubService;
import com.yonyou.iuap.duban.entity.Duban;
import com.yonyou.iuap.duban.service.DubanService;
import com.yonyou.iuap.baseservice.ref.service.RefCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 * 说明：平台-督办任务主表 打印Controller——提供数据打印回调rest接口
 * 
 * @date 2019-3-15 10:25:42
 */
@Controller
@RequestMapping(value="/DUBAN")
public class DubanPrintController extends GenericPrintController<Duban>{

    private Logger logger = LoggerFactory.getLogger(DubanController.class);


    private DubanService service;
    @Autowired
    public void setService(DubanService service) {
        this.service = service;
        super.setService(service);
    }

  
    private DubanSubService DubanSubService;
    @Autowired
    public void setDubanSubService(DubanSubService DubanSubService) {
        this.DubanSubService = DubanSubService;
        super.setSubService("DUBAN_SUB",DubanSubService);
    }

}
