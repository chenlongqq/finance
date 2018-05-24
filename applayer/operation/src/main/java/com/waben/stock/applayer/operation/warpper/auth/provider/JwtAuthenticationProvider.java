package com.waben.stock.applayer.operation.warpper.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
* @author yuyidi 2017-07-21 16:01:52
* @class com.wangbei.awre.auth.provider.JwtAuthenticationProvider
* @description jwt认证提供器
*/
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
