package com.yonyou.iuap.order_info.entity;

import com.yonyou.iuap.baseservice.print.entity.Printable;

import java.io.Serializable;

/**
 * @author guoxh
 * @date 2019/4/25 16:04
 * @desc TODO
 */
public class PrintTest implements Printable, Serializable {

    private String confirmTime;
    private String orderType;
    private String orderNo;

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String getMainBoCode() {
        return "print_test";
    }

    @Override
    public Serializable getId() {
        return null;
    }

    @Override
    public void setId(Serializable serializable) {

    }

    @Override
    public String getCreateTime() {
        return null;
    }

    @Override
    public void setCreateTime(String s) {

    }

    @Override
    public String getCreateUser() {
        return null;
    }

    @Override
    public void setCreateUser(String s) {

    }

    @Override
    public String getLastModified() {
        return null;
    }

    @Override
    public void setLastModified(String s) {

    }

    @Override
    public String getLastModifyUser() {
        return null;
    }

    @Override
    public void setLastModifyUser(String s) {

    }

    @Override
    public String getTs() {
        return null;
    }

    @Override
    public void setTs(String s) {

    }
}
