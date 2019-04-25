package com.yonyou.iuap.order_info.entity;

import com.yonyou.iuap.baseservice.print.entity.Printable;

import java.io.Serializable;

/**
 * @author guoxh
 * @date 2019/4/25 16:04
 * @desc 云打印必须把主表的getId setId 设置好,
 * 否则主子表关联的时候因为主表ID为空会导致关联不起来导致前端报错
 */
public class PrintTest implements Printable, Serializable {

    private String id;
    private String confirmTime;
    private String orderType;
    private String orderNo;

//    private String createTime;
//    private String createUser;
//    private String lastModified;
//    private String lastModifyUser;
//    private String ts;

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
        return id;
    }

    @Override
    public void setId(Serializable serializable) {
        this.id = (String) serializable;
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
    /*@Override
    public Serializable getId() {
        return id;
    }

    @Override
    public void setId(Serializable id) {
        this.id = (String) id;
    }

    @Override
    public String getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(String s) {
        this.createTime = s;
    }

    @Override
    public String getCreateUser() {
        return createUser;
    }

    @Override
    public void setCreateUser(String s) {
        this.createUser =s ;
    }

    @Override
    public String getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(String s) {
        this.lastModified = s;
    }

    @Override
    public String getLastModifyUser() {
        return lastModifyUser;
    }

    @Override
    public void setLastModifyUser(String s) {
        this.lastModifyUser = s;
    }

    @Override
    public String getTs() {
        return ts;
    }

    @Override
    public void setTs(String s) {
        this.ts = s;
    }*/
}
