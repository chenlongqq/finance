package com.waben.stock.applayer.operation.warpper.auth.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
* @author yuyidi 2017-11-15 10:33:34
* @class com.waben.stock.applayer.operation.warpper.auth.provider.ManagerAuthenticationProvider
* @description 管理及运营人员认证器
*/
public class ManagerAuthenticationProvider extends DaoAuthenticationProvider {


    public ManagerAuthenticationProvider(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }


    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        logger.info("管理用户认证");
        return super.retrieveUser(username,usernamePasswordAuthenticationToken);
    }


}
