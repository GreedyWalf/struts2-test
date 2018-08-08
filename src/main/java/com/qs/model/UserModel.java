package com.qs.model;

import com.qs.common.base.BaseModel;

import java.util.Date;

/**
 * struts模型驱动类：接收客户端请求的参数，并封装为指定model对象
 */
public class UserModel extends BaseModel {

    private String userId;
    private String userName;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
