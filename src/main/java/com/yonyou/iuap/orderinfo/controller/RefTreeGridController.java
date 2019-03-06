package com.yonyou.iuap.orderinfo.controller;

import iuap.ref.sdk.refmodel.model.AbstractTreeGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class RefTreeGridController extends AbstractTreeGridRefModel {
    private Logger logger = LoggerFactory.getLogger(RefTreeGridController.class);


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
    public List<Map<String, String>> blobRefClassSearch(RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    public Map<String, Object> blobRefSearch(RefViewModelVO refViewModelVO) {
        return null;
    }
}
