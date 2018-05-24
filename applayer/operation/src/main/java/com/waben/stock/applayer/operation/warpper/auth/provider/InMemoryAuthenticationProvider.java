package com.waben.stock.applayer.operation.warpper.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/7/20.
 * @desc
 */
@Component
public class InMemoryAuthenticationProvider implements AuthenticationProvider {

    private final String name = "root";
    private final String password = "root";
    private final List<GrantedAuthority> authorities = new ArrayList<>();
//    {
//        authorities.add(new RoleAuthority("ROLE_ADMIN"));
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        AccountCredentials accountCredentials = (AccountCredentials) authentication.getDetails();
        if (isMatch(authentication)) {
            User user = new User(authentication.getName(), authentication.getCredentials().toString(), authorities);
            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean isMatch(Authentication authentication) {
        if (authentication.getName().equals(name) && authentication.getCredentials().equals(password))
            return true;
        else
            return false;
    }
}
