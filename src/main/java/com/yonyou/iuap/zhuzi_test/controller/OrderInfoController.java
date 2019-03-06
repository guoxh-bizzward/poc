package com.yonyou.iuap.zhuzi_test.controller;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.zhuzi_test.service.OrderInfoEnumService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.WordUtils;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.collections.MapUtils;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.zhuzi_test.entity.OrderInfo;
import com.yonyou.iuap.zhuzi_test.service.OrderInfoService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mvc.type.JsonResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;
/**
 * 说明：单表orderinfo 基础Controller——提供数据增、删、改、查、导入导出等rest接口
 * 
 * @date 2019-2-21 15:53:03
 */
@Controller
@RequestMapping(value="/order_info")
public class OrderInfoController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    private OrderInfoService orderInfoService;


    @Autowired
    private OrderInfoEnumService orderInfoEnumService;

    @Autowired
    public void setOrderInfoService(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    private static final String MODELCODE = "orderInfo";


    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(PageRequest pageRequest, SearchParams searchParams) {
        Page<OrderInfo> page = this.orderInfoService.selectAllByPage(pageRequest, searchParams);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", page);
        return this.buildMapSuccess(map);
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public Object get(PageRequest pageRequest, SearchParams searchParams) {
        String id = MapUtils.getString(searchParams.getSearchMap(), "id");
        if (id==null){
            return this.buildSuccess();//前端约定传空id则拿到空对象
        }
        if(StrUtil.isBlank(id)) {
            return this.buildError("msg", "主键id参数为空!", RequestStatusEnum.FAIL_FIELD);
        }else {
            OrderInfo entity = this.orderInfoService.findById(id);
            return this.buildSuccess(entity);
        }
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestBody OrderInfo entity) {
        JsonResponse jsonResp;
        try {
            this.orderInfoService.save(entity);
            jsonResp = this.buildSuccess(entity);
        }catch(Exception exp) {
            jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
        }
        return jsonResp;
    }

    @RequestMapping(value = "/saveBatch")
    @ResponseBody
    public Object saveBatch(@RequestBody List<OrderInfo> listData) {
        this.orderInfoService.saveBatch(listData);
        return this.buildSuccess();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestBody OrderInfo entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.orderInfoService.delete(entity);
        return super.buildSuccess();
    }

    @RequestMapping(value = "/deleteBatch")
    @ResponseBody
    public Object deleteBatch(@RequestBody List<OrderInfo> listData, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.orderInfoService.deleteBatch(listData);
        return super.buildSuccess();
    }

    @RequestMapping("/selectData")
    @ResponseBody
    public Object selectData(){
        List<Map> list = orderInfoEnumService.selectData();

        return JSONArray.fromObject(list);
    }


}