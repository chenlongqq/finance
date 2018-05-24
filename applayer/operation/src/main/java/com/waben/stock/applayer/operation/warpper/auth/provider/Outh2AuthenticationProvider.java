package com.waben.stock.applayer.operation.warpper.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
* @author yuyidi 2017-07-13 09:34:25
* @class com.wangbei.awre.auth.provider.Outh2AuthenticationProvider
* @description outh提供认证实现
*/
public class Outh2AuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
