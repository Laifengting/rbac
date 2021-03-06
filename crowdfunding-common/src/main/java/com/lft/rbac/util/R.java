package com.lft.rbac.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class R implements Serializable {
    private static final long serialVersionUID = 1L;
    // @ApiModelProperty (value = "是否成功")
    private Boolean success;
    
    // @ApiModelProperty (value = "返回码")
    private Integer code;
    
    // @ApiModelProperty (value = "返回消息")
    private String message;
    
    // @ApiModelProperty (value = "返回数据")
    private Map<String, Object> data = new HashMap<>();
    
    /**
     * 构造方法私有化
     */
    private R() {
    }
    
    /**
     * 链式编程
     * R.OK().success(true).data(10).message("调用成功").code(ResultCode.SUCCESS);
     */
    
    /**
     * 提供静态的公共方法。
     */
    public static R OK() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
    
    public static R ERROR() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }
    
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
    
    public R message(String message) {
        this.setMessage(message);
        return this;
    }
    
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }
    
    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
    
    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
    
    public Boolean getSuccess() {
        return success;
    }
    
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
