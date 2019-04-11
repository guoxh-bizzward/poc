package com.yonyou.iuap.duban.controller;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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
import com.yonyou.iuap.duban.entity.Duban;
import com.yonyou.iuap.duban.service.DubanService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mvc.type.JsonResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.hutool.core.util.StrUtil;
import com.yonyou.iuap.duban.service.DubanAssoService;
import com.yonyou.iuap.baseservice.vo.GenericAssoVo;
import com.yonyou.iuap.baseservice.entity.annotation.Associative;
import org.springframework.util.StringUtils;
import com.yonyou.iuap.common.utils.ExcelExportImportor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 说明：平台-督办任务主表 基础Controller——提供数据增、删、改、查、导入导出等rest接口
 * 
 * @date 2019-3-15 10:25:42
 */
@Controller
@RequestMapping(value="/DUBAN")
public class DubanController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(DubanController.class);

    private DubanService dubanService;

    @Autowired
    public void setDubanService(DubanService dubanService) {
        this.dubanService = dubanService;
    }

        private DubanAssoService dubanAssoService;

    @Autowired
    public void setDubanAssoService(DubanAssoService dubanAssoService) {
        this.dubanAssoService = dubanAssoService;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(PageRequest pageRequest, SearchParams searchParams) {
        Page<Duban> page = this.dubanService.selectAllByPage(pageRequest, searchParams);
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
            Duban entity = this.dubanService.findById(id);
            return this.buildSuccess(entity);
        }
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestBody Duban entity) {
        JsonResponse jsonResp;
        try {
            this.dubanService.save(entity);
            jsonResp = this.buildSuccess(entity);
        }catch(Exception exp) {
            jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
        }
        return jsonResp;
    }

    @RequestMapping(value = "/saveBatch")
    @ResponseBody
    public Object saveBatch(@RequestBody List<Duban> listData) {
        this.dubanService.saveBatch(listData);
        return this.buildSuccess();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestBody Duban entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.dubanService.delete(entity);
        return super.buildSuccess();
    }

    @RequestMapping(value = "/deleteBatch")
    @ResponseBody
    public Object deleteBatch(@RequestBody List<Duban> listData, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.dubanService.deleteBatch(listData);
        return super.buildSuccess();
    }

        /**
     * 主子表合并处理--主表单条查询
     * @see com.yonyou.iuap.baseservice.controller.GenericAssoController
     * @param pageRequest
     * @param searchParams
     * @return GenericAssoVo ,entity中保存的是单条主表数据,sublist中保存的是字表数据,一次性全部加载
     */
        @RequestMapping(value = "/getAssoVo")
    @ResponseBody
    public Object  getAssoVo(PageRequest pageRequest,
                             SearchParams searchParams){

        Serializable id = MapUtils.getString(searchParams.getSearchMap(), "id");
        if (null==id){ return buildSuccess();}
        GenericAssoVo vo = dubanAssoService.getAssoVo(pageRequest, searchParams);
        JsonResponse result = this.buildSuccess("entity",vo.getEntity());//保证入参出参结构一致
        result.getDetailMsg().putAll(vo.getSublist());
        return  result;
    }
    /**
     * 主子表合并处理--主表单条保存
     * @see com.yonyou.iuap.baseservice.controller.GenericAssoController
     * @param vo GenericAssoVo ,entity中保存的是单条主表数据,sublist中保存的是字表数据
     * @return 主表的业务实体
     */
    @RequestMapping(value = "/saveAssoVo")
    @ResponseBody
    public Object  saveAssoVo(@RequestBody GenericAssoVo<Duban> vo){
        Associative annotation= vo.getEntity().getClass().getAnnotation(Associative.class);
        if (annotation==null|| StringUtils.isEmpty(annotation.fkName())){
            return buildError("","Nothing got @Associative or without fkName",RequestStatusEnum.FAIL_FIELD);
        }
        Object result =dubanAssoService.saveAssoVo(vo,annotation);
        return this.buildSuccess(result) ;
    }
    /**
     * 主子表合并处理--主表单条删除,默认级联删除子表,若取消级联删除请在主实体上注解改为@Associative(fkName = "fkIdYgdemoYwSub",deleteCascade = false)
     * @see com.yonyou.iuap.baseservice.controller.GenericAssoController
     * @param entities 待删除业务实体
     * @return 删除成功的实体
     */
    @RequestMapping(value = "/deleAssoVo")
    @ResponseBody
    public Object  deleAssoVo(@RequestBody Duban... entities){
        if (entities.length==0){
            return this.buildGlobalError("requst entity must not be empty");
        }
        Associative annotation = entities[0].getClass().getAnnotation(Associative.class);
        if (annotation != null && !StringUtils.isEmpty(annotation.fkName())) {
            int result =0;
            for (Duban entity:entities){
                if (StringUtils.isEmpty(entity.getId())) {
                    return this.buildError("ID", "ID field is empty:"+entity.toString(), RequestStatusEnum.FAIL_FIELD);
                } else if (StringUtils.isEmpty(entity.getTs())) {
                    return this.buildError("TS", "TS field is empty:"+entity.toString(), RequestStatusEnum.FAIL_FIELD);
                } else {
                    result += this.dubanAssoService.deleAssoVo(entity, annotation);
                }
            }
            return this.buildSuccess(result + " rows(both entity and its sub-entities) has been deleted!");
        } else {
            return this.buildError("", "Nothing got @Associative or without fkName", RequestStatusEnum.FAIL_FIELD);
        }
    }

    @RequestMapping(value = "/excelTemplateDownload", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, String> excelTemplateDownload(HttpServletRequest request,
            HttpServletResponse response){
        Map<String, String> result = new HashMap<String, String>();

        try {
            ExcelExportImportor.downloadExcelTemplate(response, getImportHeadInfo(), "平台-督办任务主表", "平台-督办任务主表模板");
            result.put("status", "success");
            result.put("msg", "Excel模版下载成功");
        } catch (Exception e) {
            logger.error("Excel模版下载失败", e);
            result.put("status", "failed");
            result.put("msg", "Excel模版下载失败");
        }
        return result;
    }

    @RequestMapping(value = "/toImportExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> importExcel(HttpServletRequest request){
        Map<String, String> result = new HashMap<String, String>();
        try {

            List<Duban> list = new ArrayList<Duban>();
            CommonsMultipartResolver resolver = new CommonsMultipartResolver();
            if(resolver.isMultipart(request)){
                 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                 int size = multipartRequest.getMultiFileMap().size();
                 MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
                 if(multiValueMap !=null && size > 0){
                    for(MultiValueMap.Entry<String, List<MultipartFile>> me : multiValueMap.entrySet()){
                        List<MultipartFile> multipartFile = me.getValue();
                        for(MultipartFile mult : multipartFile){
                            String multName =  mult.getOriginalFilename().toString();
                            String multTypeName = multName.substring(multName.lastIndexOf(".")+1, multName.length());
                            if((multTypeName != "xlsx" && !"xlsx".equals(multTypeName)) && (multTypeName != "xls" && !"xls".equals(multTypeName))){
                                throw new Exception("导入数据格式异常！");
                            }
                            list = ExcelExportImportor.loadExcel(mult.getInputStream(), getImportHeadInfo(), Duban.class);
                            if(list==null || list.size()== 0){
                                throw new Exception("导入数据异常！");
                            }
                        }
                    }
                 }
            }
            dubanService.saveBatch(list);
            result.put("status", "success");
            result.put("msg", "Excel导入成功");
        } catch (Exception e) {
            logger.error("Excel导入失败", e);
            result.put("status", "failed");
            result.put("msg", e.getMessage()!=null?e.getMessage():"Excel导入失败");
        }
        return result;
    }

    @RequestMapping(value = "/toExportExcel",method = RequestMethod.POST)
    @ResponseBody
    public Object exportExcel(PageRequest pageRequest,
                        @FrontModelExchange(modelType = Duban.class) SearchParams searchParams,HttpServletResponse response,@RequestBody List<Duban> dataList){

       Map<String, String> result = new HashMap<String, String>();
       try {
          List idsList = new ArrayList();
          for (Duban entity : dataList) {
             idsList.add(entity.getId());
          }
          List list = dubanService.selectListByExcelData(idsList);
          ExcelExportImportor.writeExcel(response, list, getExportHeadInfo(), "平台-督办任务主表", "平台-督办任务主表");
          result.put("status", "success");
          result.put("msg", "信息导出成功");
          result.put("fileName", "平台-督办任务主表");
       } catch (Exception e) {
          logger.error("Excel下载失败", e);
          result.put("status", "failed");
          result.put("msg", "Excel下载失败");
       }
       return result;
    }

    @RequestMapping(value = "/toExportExcelAll",method = RequestMethod.GET)
    @ResponseBody
    public Object exportExcelAll(PageRequest pageRequest,
                        @FrontModelExchange(modelType = Duban.class) SearchParams searchParams,HttpServletResponse response){

       Map<String, String> result = new HashMap<String, String>();
       try {
          Page<Duban> page = dubanService.selectAllByPage(pageRequest, searchParams);
          List list = page.getContent();
          if(list == null || list.size()==0){
              throw new Exception("没有导出数据！");
          }
          ExcelExportImportor.writeExcel(response, list, getExportHeadInfo(), "平台-督办任务主表", "平台-督办任务主表");
          result.put("status", "success");
          result.put("msg", "信息导出成功");
       } catch (Exception e) {
          logger.error("Excel下载失败", e);
          result.put("status", "failed");
          result.put("msg", "Excel下载失败");
       }
       return result;
    }

    private Map<String, String> getExportHeadInfo() {
        String values = "{'code':'督办编号','endDate':'计划结束时间','xbrName':'协办人','zbrName':'主办人','rwpf':'任务评分','lyCodeEnumValue':'督办来源','qtLd':'牵头领导','zrdwName':'责任单位','jdBl':'进度比例','stateEnumValue':'状态','dbInfo':'督办事宜','kpiLevelEnumValue':'kpi级别','jfyq':'交付要求','xbDwName':'协办单位','beginDate':'计划开始时间','kpiFlagEnumValue':'是否kpi','lySm':'备注','dbr':'督办人','name':'督办名称','unitIdName':'所属组织','zyCdEnumValue':'重要程度','zrrName':'责任人',}";
        return getMapInfo(values);
    }

    private Map<String, String> getImportHeadInfo() {
        String values = "{'code':'督办编号','endDate':'计划结束时间','xbr':'协办人','zbr':'主办人','rwpf':'任务评分','lyCode':'督办来源','qtLd':'牵头领导','zrDw':'责任单位','jdBl':'进度比例','state':'状态','dbInfo':'督办事宜','kpiLevel':'kpi级别','jfyq':'交付要求','xbDw':'协办单位','beginDate':'计划开始时间','kpiFlag':'是否kpi','lySm':'备注','dbr':'督办人','name':'督办名称','unitid':'所属组织','zyCd':'重要程度','zrr':'责任人',}";
        return getMapInfo(values);
    }

    private Map<String, String> getMapInfo(String values){
        String values_new = values.substring(0, values.length()-1);
        if(values_new.endsWith(",")){
                values = values_new.substring(0, values_new.length()-1)+"}";
        }
        Map<String, String> headInfo = null;
        //if (headInfo == null) {
        JSONObject json = JSONObject.fromObject(values);
        headInfo = (Map<String, String>) json;
        //}
        return headInfo;
    }


}