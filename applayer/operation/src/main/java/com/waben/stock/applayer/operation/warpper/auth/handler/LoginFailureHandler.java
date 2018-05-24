package com.waben.stock.applayer.operation.warpper.auth.handler;

import com.waben.stock.interfaces.pojo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yuyidi 2017-07-13 15:05:46
 * @class com.wangbei.awre.auth.handler.LoginFailureHandler
 * @description 异步验证失败通知
 */
@Component
public class LoginFailureHandler extends ResponseHandler implements AuthenticationFailureHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logger.info("登陆验证失败:错误原因：{}",exception.getMessage());
        //此处判断是app 还是 pc端web页面  选择是跳转页面还是返回json
        response.setStatus(HttpServletResponse.SC_OK);
        Response<Object> result = new Response<>();
        result.setMessage(exception.getMessage());
        result.setCode("500");
        response(result,request,response,"/login-error");
    }


}
