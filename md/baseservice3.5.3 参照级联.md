##参照级联

###自定义参照级联

1.自定义参照级联需要自己手写一套参照方法,此处可以参考demo示例中的`com.yonyou.iuap.orderinfo.controller.RefUseDefineController`类,然后在`ref_refinfo`中将refurl指向自定义的controller的requestMapping上.

2.前端代码在`param`对象内可以增加`pk_org`,`pk_group`,`pk_user`,`pk_val`字段,其中前三个也是 系统参照做级联的字段,在做自定义级联的时候也可以指定,`pk_val`为数组模式,此处我们使用`pk_val`进行演示

```javascript
param: {//url请求参数
    pk_val:refKeyArraypurOrg,
    refCode: 'bd_common_currency',
    tenantId: '',
    sysId: '',
    transmitParam: '6',
    locale:getCookie('u_locale'),
    //clientParam: '{"isUseDataPower":"true"}'
},

```

3.后台在`blobRefSearch`方法中增加一段代码,该部分代码可以根据实际情况进行修改,只要保证refParamConfigTable的每个condition 是一个正常的查询条件即可.

```java
String[] pkVal = refModel.getPk_val();
if(refModel.getRefCode().equals("bd_common_currency")){
    List<String> params = new ArrayList<>();
    //如果上一个组件获取的值为空,则本处的参照不显示值
    if(pkVal == null || pkVal.length ==0){
        params.add("and 1=2");
    }
    //将上一组件获取的值作为查询条件带入
    params.add("and id != '"+ pkVal[0]+"'");
    refParamConfigTable.setCondition(params);
}
```

配置完成后,该参照就可以根据用户指定的内容或者控件值进行过滤查询