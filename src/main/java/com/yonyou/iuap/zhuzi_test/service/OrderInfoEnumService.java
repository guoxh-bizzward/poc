package com.yonyou.iuap.zhuzi_test.service;
import com.yonyou.iuap.zhuzi_test.entity.OrderInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.yonyou.iuap.baseservice.persistence.support.QueryFeatureExtension;
import com.yonyou.iuap.mvc.type.SearchParams;

@Service
public class OrderInfoEnumService implements QueryFeatureExtension<OrderInfo>{
    
                private static Map<String, String> orderTypeMap = new HashMap<String, String>();
                private static Map<String, String> orderStateMap = new HashMap<String, String>();
        static{         
                orderTypeMap.put("0", "生产订单");
                orderTypeMap.put("1", "日常订单");
                orderTypeMap.put("2", "临时订单");
                orderTypeMap.put("3", "测试订单");
                orderStateMap.put("0", "未提交");
                orderStateMap.put("1", "已提交");
        }
    
        
        @Override
        public List<OrderInfo> afterListQuery(List<OrderInfo> list) {
                List<OrderInfo> resultList = new ArrayList<OrderInfo>();      
        for (OrderInfo entity : list) {
                        if(entity.getOrderType() != null){
                                String value = orderTypeMap.get(entity.getOrderType().toString());
                                entity.setOrderTypeEnumValue(value);
                        }
                        if(entity.getOrderState() != null){
                                String value = orderStateMap.get(entity.getOrderState().toString());
                                entity.setOrderStateEnumValue(value);
                        }
                        resultList.add(entity);
                }
        
        return resultList;
        }

    public List<Map> fillDynamicList(List<Map> list) {
        for (Map<String, Object> item : list) {
            if(item.get("orderType") != null){
                item.put("orderTypeEnumValue",orderTypeMap.get( String.valueOf(item.get("orderType") )  ));
            }
            if(item.get("orderState") != null){
                item.put("orderStateEnumValue",orderStateMap.get( String.valueOf(item.get("orderState") )  ));
            }
        }
        return list;
    }
        @Override
        public SearchParams prepareQueryParam(SearchParams searchParams, Class modelClass) {
                return searchParams;
        }
}

