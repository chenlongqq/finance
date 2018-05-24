package com.waben.stock.applayer.operation.warpper.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/***
* @author yuyidi 2017-11-15 10:44:49
* @class com.waben.stock.applayer.operation.warpper.auth.UPTypeAuthenticationToken
* @description 用户密码及不同类型用户验证类
*/
public class UPTypeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private Boolean isOperater;

    public UPTypeAuthenticationToken(Object principal, Object credentials,Boolean isOperater) {
        super(principal, credentials);
        this.isOperater = isOperater;
    }

    public UPTypeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,Boolean isOperater) {
        super(principal, credentials, authorities);
        this.isOperater = isOperater;
    }

    public Boolean getOperater() {
        return isOperater;
    }
}
