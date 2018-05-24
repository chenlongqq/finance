package com.waben.stock.applayer.operation.warpper.auth.provider;

import com.waben.stock.applayer.operation.warpper.auth.UPTypeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * @author yuyidi 2017-11-15 10:33:14
 * @class com.waben.stock.applayer.operation.warpper.auth.provider.InvestorAuthenticationProvider
 * @description 投资人认证器
 */
public class InvestorAuthenticationProvider extends DaoAuthenticationProvider {

    public InvestorAuthenticationProvider(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken
            usernamePasswordAuthenticationToken) throws AuthenticationException {
        logger.info("投资人用户认证");
        UPTypeAuthenticationToken upTypeAuthenticationToken = (UPTypeAuthenticationToken)
                usernamePasswordAuthenticationToken;
        if (upTypeAuthenticationToken.getOperater()) {
            logger.info("当前选择管理员登录，忽略投资人信息获取");
            throw new UsernameNotFoundException("管理员入口，忽略投资人登录");
        }
        return super.retrieveUser(username, upTypeAuthenticationToken);
    }
}
