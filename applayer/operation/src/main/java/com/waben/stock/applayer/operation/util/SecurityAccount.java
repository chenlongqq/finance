package com.waben.stock.applayer.operation.util;

import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
public class SecurityAccount {

    public static AccountCredentials current() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        AccountCredentials accountCredentials = (AccountCredentials) token.getPrincipal();
        return accountCredentials;
    }
}
