/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.waben.stock.applayer.operation.warpper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waben.stock.applayer.operation.warpper.auth.filter.LoginProcessingFilter;
import com.waben.stock.applayer.operation.warpper.auth.handler.*;
import com.waben.stock.applayer.operation.warpper.auth.provider.InvestorAuthenticationProvider;
import com.waben.stock.applayer.operation.warpper.auth.provider.ManagerAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

/**
 * @author Joe Grandja
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENTRY_POINT = "/login";
    @Autowired
    private InvestorAuthenticationProvider investorAuthenticationProvider;
    @Autowired
    private ManagerAuthenticationProvider managerAuthenticationProvider;
    @Autowired
    private LoginSuccessHandler successHandler;
    @Autowired
    private LoginFailureHandler failureHandler;
    @Autowired
    private ObjectMapper objectMapper;

    @Resource
    private SessionRegistry sessionRegistry;

    protected LoginProcessingFilter processingFilter() throws Exception {
        LoginProcessingFilter filter = new LoginProcessingFilter(LOGIN_ENTRY_POINT, successHandler, failureHandler,
                objectMapper, sessionRegistry);
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_ENTRY_POINT, "POST"));
        filter.setPostOnly(true);
        filter.setAuthenticationManager(authenticationManager());
        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }

    /**
     * 配置通过拦截器请求
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .and().headers().frameOptions().disable()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, LOGIN_ENTRY_POINT, "/login-error").permitAll()
                .antMatchers(HttpMethod.GET, "/turbine/**", "/turbine/hystrix.stream", "/hystrix.stream",
                        "/file/upload", "/websocket/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                .antMatchers(HttpMethod.POST, "/file/**").permitAll()
//		        .antMatchers("/offlinestockoptiontrade/**").permitAll()
                .anyRequest().authenticated()
//                .anyRequest().permitAll()
                .and().formLogin().loginPage(LOGIN_ENTRY_POINT)
//                    .successForwardUrl("/index")
                .and().logout().invalidateHttpSession(false).logoutUrl("/logout").logoutSuccessHandler(new
                LogoutSuccessHandler())
                .and().rememberMe()
                .and().exceptionHandling()
                .authenticationEntryPoint(new ForbiddenEntryPoint())
                .accessDeniedHandler(new ForbiddenAccessDeniedHandler())
                .and()
                .addFilterAt(processingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new ConcurrentSessionFilter(sessionRegistry, LOGIN_ENTRY_POINT),
                        ConcurrentSessionFilter.class)
//                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
//                .httpBasic().and().sessionManagement().maximumSessions(1).expiredUrl(LOGIN_ENTRY_POINT)
        ;

    }

    /**
     * 配置 Security Filter 链
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/img/**", "/static/**", "**/*.css", "**/*.js", "/favicon.ico");
    }


    /**
     * 验证过程 若多个authenticationProvider 返回null 则交给下一个provider验证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(investorAuthenticationProvider)
                .authenticationProvider(managerAuthenticationProvider);
        auth.eraseCredentials(false);
    }


}
