package com.yonyou.iuap.order_info.controller;
import com.yonyou.iuap.baseservice.print.controller.GenericPrintController;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.baseservice.entity.annotation.Associative;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.order_info.entity.OrderInfoNew;
import com.yonyou.iuap.order_info.entity.PrintTest;
import com.yonyou.iuap.order_info.service.OrderInfoNewService;
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
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 说明：orderinfo_new 打印Controller——提供数据打印回调rest接口
 * 
 * @date 2019-4-17 21:33:43
 */
@Controller
@RequestMapping(value="/order_info_new")
public class OrderInfoNewPrintController extends GenericPrintController<OrderInfoNew>{

    private Logger logger = LoggerFactory.getLogger(OrderInfoNewController.class);


    private OrderInfoNewService service;
    @Autowired
    public void setService(OrderInfoNewService service) {
        this.service = service;
        super.setService(service);
    }

    @Override
    public Object getDataForPrint(HttpServletRequest request) {
        String params = request.getParameter("params");
        JSONObject jsonObj = JSON.parseObject(params);
        String id = (String) jsonObj.get("id");

        PrintTest vo = new PrintTest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        vo.setId(id);
        vo.setConfirmTime(sdf.format(new Date()));
        vo.setOrderNo("123");
        vo.setOrderType("111");
        vo.setCreateTime(sdf.format(new Date()));
        vo.setCreateUser("1234");
        vo.setLastModified(sdf.format(new Date()));
        vo.setTs("1");

        if (vo.getMainBoCode()==null){
            return buildError("mainBoCode","主表业务对象编码为打印关键参数不可为空",RequestStatusEnum.FAIL_FIELD);
        }


        JSONObject jsonVo = JSONObject.parseObject(JSONObject.toJSON(vo).toString());

        JSONObject mainData = new JSONObject();
        JSONObject childData = new JSONObject();

        JSONArray mainDataJson = new JSONArray();// 主实体数据


        Set<String> setKey = jsonVo.keySet();
        for(String key : setKey ){
            String value = jsonVo.getString(key);
            mainData.put(key, value);
        }
        mainDataJson.add(mainData);// 主表只有一行


        //增加子表的逻辑

        JSONObject boAttr = new JSONObject();
        //key：主表业务对象code
        boAttr.put(vo.getMainBoCode(), mainDataJson);

        /*for (String subBoCode:subServices.keySet()){
            Associative associative= vo.getClass().getAnnotation(Associative.class);
            if (associative==null|| StringUtils.isEmpty(associative.fkName())){
                return buildError("","主子表打印需要在entity上增加@Associative并指定fkName",RequestStatusEnum.FAIL_FIELD);
            }
            List subList= subServices.get(subBoCode).queryList(associative.fkName(),id);
            JSONArray childrenDataJson = new JSONArray();
            childrenDataJson.addAll(subList);
            boAttr.put(subBoCode, childrenDataJson);//子表填充
        }*/


        logger.debug("打印回调数据:"+boAttr.toString());
        return boAttr.toString();
    }
}
