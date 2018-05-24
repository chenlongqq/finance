package com.waben.stock.applayer.operation.warpper.auth.handler;

import com.waben.stock.interfaces.pojo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author yuyidi 2017-07-20 17:16:32
* @class com.wangbei.awre.auth.handler.ForbiddenEntryPoint
* @description 未登陆禁止访问
*/
public class ForbiddenEntryPoint extends ResponseHandler implements AuthenticationEntryPoint {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException
            authException) throws IOException, ServletException {
        logger.warn("用户未登陆状态访问需要登陆接口，请求地址:{}",request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Response<Object> result = new Response<>();
        result.setMessage("未登陆，请先登陆");
        result.setCode("403");
        response(result,request,response,"/login");
    }
}
