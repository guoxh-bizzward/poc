package com.yonyou.iuap.order_info.service;
import com.yonyou.iuap.order_info.entity.OrderInfoNew;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.yonyou.iuap.baseservice.persistence.support.QueryFeatureExtension;
import com.yonyou.iuap.mvc.type.SearchParams;

@Service
public class OrderInfoNewEnumService implements QueryFeatureExtension<OrderInfoNew>{
    
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
        public List<OrderInfoNew> afterListQuery(List<OrderInfoNew> list) {
                List<OrderInfoNew> resultList = new ArrayList<OrderInfoNew>();      
        for (OrderInfoNew entity : list) {
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
        @Override
        public SearchParams prepareQueryParam(SearchParams searchParams, Class modelClass) {
                return searchParams;
        }
}

