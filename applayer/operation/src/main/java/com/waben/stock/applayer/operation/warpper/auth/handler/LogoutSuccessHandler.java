package com.waben.stock.applayer.operation.warpper.auth.handler;

import com.waben.stock.interfaces.pojo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Created by yuyidi on 2017/7/20.
 * @desc
 */
public class LogoutSuccessHandler extends ResponseHandler implements org.springframework.security.web.authentication
        .logout.LogoutSuccessHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication
            authentication) throws IOException, ServletException {
        logger.info("成功登出");
        response.setStatus(HttpServletResponse.SC_OK);
        Response<Object> result = new Response<>();
        result.setMessage("用户注销成功");
        result.setCode("200");
        response(result,request,response,"/login");
    }
}
