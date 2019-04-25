package com.yonyou.iuap.order_info.entity;

import com.yonyou.iuap.baseservice.print.entity.Printable;

import java.io.Serializable;

/**
 * @author guoxh
 * @date 2019-04-25 17:00
 * @desc
 */
public class PrintSubTest implements Printable, Serializable {
    private String id;
    private String name;
    private String code;
    private String value;
    private String sub_id;


//    private String createTime;
//    private String createUser;
//    private String lastModified;
//    private String lastModifyUser;
//    private String ts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getMainBoCode() {
        return "sub_table";
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
}
