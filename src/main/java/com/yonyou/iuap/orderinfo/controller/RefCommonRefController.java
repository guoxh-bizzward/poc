package com.yonyou.iuap.orderinfo.controller;

import iuap.ref.sdk.refmodel.model.AbstractCommonRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

import java.util.List;
import java.util.Map;

public class RefCommonRefController extends AbstractCommonRefModel {
    @Override
    public List<Map<String, String>> matchPKRefJSON(RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    public List<Map<String, String>> filterRefJSON(RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    public List<Map<String, String>> matchBlurRefJSON(RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    public Map<String, Object> getCommonRefData(RefViewModelVO refViewModelVO) {
        return null;
    }
}
