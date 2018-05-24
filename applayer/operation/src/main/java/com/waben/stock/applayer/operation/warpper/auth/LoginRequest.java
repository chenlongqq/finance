package com.waben.stock.applayer.operation.warpper.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @author yuyidi 2017-07-21 15:38:46
* @class com.wangbei.awre.auth.LoginRequest
* @description 用户请求登陆对象
*/
public class LoginRequest {

    public String username;
    private String password;
    private Boolean isOperator;

    @JsonCreator
    public LoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public Boolean getOperator() {
        return isOperator;
    }

    public void setOperator(Boolean operator) {
        isOperator = operator;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o){
        if(o.toString().equals(this.username))
            return true;
        return false;
    }

    @Override
    public int hashCode(){
        return username.hashCode();
    }
}
